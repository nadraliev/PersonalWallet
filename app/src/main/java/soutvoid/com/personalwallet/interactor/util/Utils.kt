package soutvoid.com.personalwallet.interactor.util

import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.TagConstraint
import soutvoid.com.personalwallet.interactor.BaseServerJob

fun JobManager.cancelAllServerJobs() {
    cancelJobsInBackground(null, TagConstraint.ANY, BaseServerJob.SERVER_JOB_TAG)
}