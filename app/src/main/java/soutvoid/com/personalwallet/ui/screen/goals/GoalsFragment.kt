package soutvoid.com.personalwallet.ui.screen.goals

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.ui.base.BaseFragment

class GoalsFragment : BaseFragment(), GoalsView {

    companion object {
        fun newInstance(): GoalsFragment = GoalsFragment()
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mGoalsPresenter: GoalsPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): GoalsPresenter =
            GoalsPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_goals
}