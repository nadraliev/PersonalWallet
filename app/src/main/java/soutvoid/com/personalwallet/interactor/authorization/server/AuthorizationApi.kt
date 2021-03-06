package soutvoid.com.personalwallet.interactor.authorization.server

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthorizationApi {

    companion object {
        const val SIGNUP_URL = "signup"
        const val LOGIN_URL = "login"
        const val REFRESH_TOKENS_URL = "tokens/refresh"
        const val VALIDATE_TOKEN_URL = "tokens/auth/validate"
    }

    @POST(SIGNUP_URL)
    fun signup(@Body userDto: UserDto): Observable<UserDto>

    @POST(LOGIN_URL)
    fun login(@Body userDto: UserDto): Observable<TokensDto>

    @GET(REFRESH_TOKENS_URL)
    fun refreshTokens(): Observable<TokensDto>

    @GET(VALIDATE_TOKEN_URL)
    fun validateToken(): Observable<Any>
}