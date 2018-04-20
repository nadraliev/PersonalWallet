package soutvoid.com.personalwallet.interactor.authorization.server

import com.birbit.android.jobqueue.RetryConstraint
import com.github.salomonbrys.kodein.instance
import com.orhanobut.logger.Logger
import soutvoid.com.personalwallet.interactor.BaseServerJob
import soutvoid.com.personalwallet.interactor.authorization.local.IAuthorizationRepository
import soutvoid.com.personalwallet.interactor.util.toEntity

class UpdateTokensJob : BaseServerJob(2) {

    private val authorizationRepository: IAuthorizationRepository
        get() = kodein.instance()
    private val authorizationApi: AuthorizationApi
        get() = kodein.instance()

    override fun onRun() {
        Logger.d("Started to update tokens")
        val newTokens = authorizationApi.refreshTokens().blockingFirst()
        authorizationRepository.deleteAll().blockingSingle(Any())
        authorizationRepository.create(newTokens.toEntity()).blockingSubscribe()
        Logger.d("Updated tokens")
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        Logger.d(throwable)
        return if (runCount < 6)
            RetryConstraint.RETRY
        else
            RetryConstraint.CANCEL
    }

    override fun onAdded() {

    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        //TODO should notify user somehow
    }
}