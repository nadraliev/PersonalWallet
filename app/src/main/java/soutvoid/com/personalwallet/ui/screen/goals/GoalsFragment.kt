package soutvoid.com.personalwallet.ui.screen.goals

import android.support.v7.widget.Toolbar
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.screen.addgoal.AddGoalActivity
import soutvoid.com.personalwallet.ui.screen.main.IToolbarAdapter

class GoalsFragment : BaseFragment(), GoalsView, IToolbarAdapter {

    companion object {
        fun newInstance(): GoalsFragment = GoalsFragment()
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mGoalsPresenter: GoalsPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): GoalsPresenter =
            GoalsPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_goals

    override fun adaptToolbar(toolbar: Toolbar) {
        activity?.title = getString(R.string.goals_title)
    }

    @OnClick(R.id.goals_add_goal_fab)
    fun onAddGoalClick() {
        mGoalsPresenter.onAddGoal()
    }

    override fun openGoalScreen(goal: Goal?) {
        context?.let {
            it.startActivity(AddGoalActivity.getIntent(it, goal?.localId))
        }
    }

    override fun showGoals(goals: List<Goal>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeGoal(goalLocalId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateGoal(goal: Goal) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}