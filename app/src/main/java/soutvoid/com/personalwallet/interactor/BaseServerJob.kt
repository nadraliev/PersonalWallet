package soutvoid.com.personalwallet.interactor

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.orhanobut.logger.Logger
import retrofit2.HttpException
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.interactor.authorization.server.UpdateTokensJob

abstract class BaseServerJob(priority: Int, delayMs: Long = 0L)
    : Job(Params(priority).delayInMs(delayMs).requireNetwork()
        .persist().setGroupId(SERVER_JOBS_GROUP_ID).addTags(SERVER_JOB_TAG)) {

    protected val kodein: Kodein
        get() = (applicationContext as App).kodein
    protected val jobManager: JobManager
        get() = kodein.instance()

    companion object {
        const val SERVER_JOBS_GROUP_ID = "serverJobs"
        const val SERVER_JOB_TAG = "serverJob"
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        Logger.d("Job $this failed. Run count: $runCount, throwable: $throwable")
        if (throwable is HttpException && (throwable.code() == 401 || throwable.code() == 403))
            jobManager.addJobInBackground(UpdateTokensJob())
        return RetryConstraint.RETRY
    }
}