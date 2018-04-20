package soutvoid.com.personalwallet.ui.screen.login

import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.util.SharedPreferencesWrapper

@InjectViewState
class LoginPresenter(kodein: Kodein) : BasePresenter<LoginView>(kodein) {

    companion object {
        const val PASSWORD_MIN_LENGTH = 8
    }

    private val sharedPreferences: SharedPreferencesWrapper by instance()

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

    }

    private fun doRegister(email: String, password: String) {

    }

}