package soutvoid.com.personalwallet.interactor.authorization.server

import com.github.salomonbrys.kodein.instance
import okhttp3.Interceptor
import okhttp3.Response
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.interactor.authorization.local.IAuthorizationRepository

class AuthorizationInterceptor : Interceptor {

    companion object {
        const val AUTHORIZATION_TOKEN_HEADER = "Authorization"
        const val REFRESH_TOKEN_HEADER = "Refresh-Token"
        const val USER_ID_TOKEN = "User-Id"
    }

    private val authorizationRepository: IAuthorizationRepository
        get() = App.instance.kodein.instance()

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val tokens = authorizationRepository.getAll().blockingFirst().toList().firstOrNull()
        tokens ?: return chain?.proceed(chain.request())

        val request = chain?.request()?.newBuilder()
                ?.addHeader(AUTHORIZATION_TOKEN_HEADER, tokens.authorizationToken)
                ?.addHeader(REFRESH_TOKEN_HEADER, tokens.refreshToken)
                ?.addHeader(USER_ID_TOKEN, tokens.userId.toString())
                ?.build()
        return chain?.proceed(request)
    }
}