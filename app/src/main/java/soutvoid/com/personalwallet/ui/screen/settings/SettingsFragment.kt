package soutvoid.com.personalwallet.ui.screen.settings

import android.widget.Switch
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.screen.login.LoginActivity

class SettingsFragment : BaseFragment(), SettingsView {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    @BindView(R.id.settings_sync_switch)
    lateinit var syncSwitch: Switch

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mSettingsPresenter: SettingsPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): SettingsPresenter =
            SettingsPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_settings

    override fun setSyncState(isSyncing: Boolean) {
        syncSwitch.isChecked = isSyncing
    }

    override fun openLoginScreen() {
        context?.let {
            startActivity(LoginActivity.getIntent(it))
        }
    }

    @OnClick(R.id.settings_sync_section)
    fun onSyncSectionClick() {
        mSettingsPresenter.onChangeSyncState(syncSwitch.isChecked)
    }
}