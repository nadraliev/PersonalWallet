package soutvoid.com.personalwallet.ui.screen.login

import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import retrofit2.HttpException
import soutvoid.com.personalwallet.domain.user.User
import soutvoid.com.personalwallet.interactor.authorization.local.IAuthorizationRepository
import soutvoid.com.personalwallet.interactor.authorization.server.AuthorizationApi
import soutvoid.com.personalwallet.interactor.util.toDto
import soutvoid.com.personalwallet.interactor.util.toEntity
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.util.SharedPreferencesWrapper

@InjectViewState
class LoginPresenter(kodein: Kodein) : BasePresenter<LoginView>(kodein) {

    companion object {
        const val PASSWORD_MIN_LENGTH = 8
    }

    private val sharedPreferences: SharedPreferencesWrapper by instance()
    private val authorizationRepository: IAuthorizationRepository by instance()
    private val authorizationApi: AuthorizationApi by instance()

    fun onLoginClick(email: String, password: String) {
        if (generalValidate(email, password)) {
            viewState?.hideLoginError()
            viewState?.setButtonsBlocked(true)
            viewState?.setProgressEnabled(true)
            doLogin(email, password)
        }
    }

    fun onRegisterClick(email: String, password: String) {
        if (generalValidate(email, password)) {
            viewState?.hideLoginError()
            viewState?.setButtonsBlocked(true)
            viewState?.setProgressEnabled(true)
            doRegister(email, password)
        }
    }

    private fun generalValidate(email: String, password: String): Boolean {
        var result = true
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            result = false
            viewState?.showEmailIsInvalidError()
        }
        if (password.length < PASSWORD_MIN_LENGTH) {
            result = false
            viewState?.showPasswordTooSmallError()
        } else if (password.isBlank()) {
            result = false
            viewState?.showPasswordIsInvalidError()
        }
        return result
    }

    private fun doLogin(email: String, password: String) {
        val userDto = User(email = email, password = password).toDto()
        val loginObservable = authorizationApi.login(userDto)
                .flatMap {
                    authorizationRepository.deleteAll()
                    Observable.just(it)
                }
                .flatMap { authorizationRepository.create(it.toEntity()) }

        loginObservable.subscribeAsyncTo(
                onNext = {
                    viewState?.setButtonsBlocked(false)
                    viewState?.setProgressEnabled(false)
                    sharedPreferences.isSyncing = true
                    sharedPreferences.userId = it.userId
                    viewState?.finish()
                },
                onError = {
                    Logger.d(it)
                    if (it is HttpException && it.code() == 401)
                        viewState?.showLoginError()
                    else
                        viewState?.showUnknownError()
                    viewState?.setButtonsBlocked(false)
                    viewState?.setProgressEnabled(false)
                }
        )
    }

    private fun doRegister(email: String, password: String) {
        val userDto = User(email = email, password = password).toDto()
        val registerObservable = authorizationApi.signup(userDto)

        registerObservable.subscribeAsyncTo(
                onNext = { doLogin(email, password) },
                onError = {
                    Logger.d(it)
                    if (it is HttpException && it.code() == 400)
                        viewState?.showEmailAlreadyRegisteredError()
                    else
                        viewState?.showUnknownError()
                    viewState?.setButtonsBlocked(false)
                    viewState?.setProgressEnabled(false)
                }
        )
    }

}