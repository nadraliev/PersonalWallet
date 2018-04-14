package soutvoid.com.personalwallet.interactor

import com.orhanobut.logger.Logger
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {

    override fun log(message: String?) {
        Logger.d(message)
    }
}