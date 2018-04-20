package soutvoid.com.personalwallet.interactor.authorization.server

import com.birbit.android.jobqueue.RetryConstraint
import com.github.salomonbrys.kodein.instance
import com.orhanobut.logger.Logger
import soutvoid.com.personalwallet.interactor.BaseServerJob
import soutvoid.com.personalwallet.interactor.util.TOKENS_VALIDATE_INTERVAL_MINUTES

class ValidateTokensJob
    : BaseServerJob(1, TOKENS_VALIDATE_INTERVAL_MINUTES * 60 * 1000L) {

    protected val authorizationApi: AuthorizationApi
        get() = kodein.instance()

    override fun onRun() {
        Logger.d("Started to validate tokens")
        authorizationApi.validateToken().blockingSingle(Any())
        Logger.d("Token validated")
        if (isCancelled)
            throw Exception("Job was cancelled")
        jobManager.addJobInBackground(ValidateTokensJob())
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return if (runCount < 6)
            super.shouldReRunOnThrowable(throwable, runCount, maxRunCount)
        else
            RetryConstraint.CANCEL
    }

    override fun onAdded() {

    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }
}