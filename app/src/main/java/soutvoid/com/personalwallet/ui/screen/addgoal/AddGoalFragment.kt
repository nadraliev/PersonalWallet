package soutvoid.com.personalwallet.ui.screen.addgoal

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import org.jetbrains.anko.support.v4.withArguments
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.common.Saveable
import soutvoid.com.personalwallet.ui.util.delegates.argument
import soutvoid.com.personalwallet.util.GOAL_ID

class AddGoalFragment : BaseFragment(), AddGoalView, Saveable {

    companion object {
        fun newInstance(goalLocalId: Long?): AddGoalFragment =
                AddGoalFragment().withArguments(GOAL_ID to goalLocalId)
    }

    private val goalLocalId by argument<Long>(GOAL_ID)

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var presenter: AddGoalPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): AddGoalPresenter = AddGoalPresenter(App.instance.kodein, goalLocalId)

    override fun getLayoutResId(): Int = R.layout.fragment_add_goal

    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fillFieldsForEdit(goal: Goal) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun finish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}