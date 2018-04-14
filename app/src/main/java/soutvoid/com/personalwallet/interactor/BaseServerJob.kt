package soutvoid.com.personalwallet.interactor

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.github.salomonbrys.kodein.Kodein
import com.orhanobut.logger.Logger
import soutvoid.com.personalwallet.app.App

abstract class BaseServerJob(priority: Int) : Job(Params(priority).requireNetwork().persist().setGroupId(SERVER_JOBS_GROUP_ID)) {

    protected val kodein: Kodein
        get() = (applicationContext as App).kodein

    companion object {
        const val SERVER_JOBS_GROUP_ID = "serverJobs"
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        Logger.d(throwable)
        return RetryConstraint.RETRY
    }
}