package soutvoid.com.personalwallet.interactor.util

import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.app.App

class GcmJobService : GcmJobSchedulerService() {

    private val mJobManager: JobManager
        get() = App.instance.kodein.instance()

    override fun getJobManager(): JobManager = mJobManager
}