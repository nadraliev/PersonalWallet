package soutvoid.com.personalwallet.ui.screen.checks

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.ui.base.BaseFragment

class ChecksFragment : BaseFragment(), ChecksView {

    companion object {
        fun newInstance(): ChecksFragment = ChecksFragment()
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mChecksPresenter: ChecksPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePesenter(): ChecksPresenter =
            ChecksPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_checks
}