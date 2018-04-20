package soutvoid.com.personalwallet.ui.screen.settings

import com.arellomobile.mvp.InjectViewState
import com.birbit.android.jobqueue.JobManager
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.interactor.util.cancelAllServerJobs
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.util.SharedPreferencesWrapper

@InjectViewState
class SettingsPresenter(kodein: Kodein) : BasePresenter<SettingsView>(kodein) {

    private val sharedPreferences: SharedPreferencesWrapper by instance()
    private val jobManager: JobManager by instance()
    private var isSyncing = false

    override fun attachView(view: SettingsView?) {
        super.attachView(view)
        showSyncState()
    }

    fun onChangeSyncState(enabled: Boolean) {
        if (isSyncing && enabled) {
            sharedPreferences.isSyncing = false
            sharedPreferences.userId = -1
            jobManager.cancelAllServerJobs()
            showSyncState()
        } else if (!isSyncing && !enabled) {
            viewState?.openLoginScreen()
        }
    }

    private fun showSyncState() {
        isSyncing = sharedPreferences.isSyncing
        viewState?.setSyncState(isSyncing)
    }
}