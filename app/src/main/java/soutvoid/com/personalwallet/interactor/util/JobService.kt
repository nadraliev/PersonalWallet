package soutvoid.com.personalwallet.interactor.util

import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.app.App

class JobService : FrameworkJobSchedulerService() {

    private val mJobManager: JobManager
        get() = (application as App).kodein.instance()

    override fun getJobManager(): JobManager = mJobManager
}