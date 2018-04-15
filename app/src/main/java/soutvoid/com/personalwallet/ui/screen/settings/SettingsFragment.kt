package soutvoid.com.personalwallet.ui.screen.settings

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.ui.base.BaseFragment

class SettingsFragment : BaseFragment(), SettingsView {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mSettingsPresenter: SettingsPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): SettingsPresenter =
            SettingsPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_settings
}