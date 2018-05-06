package soutvoid.com.personalwallet.ui.screen.goals

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.screen.addgoal.AddGoalActivity
import soutvoid.com.personalwallet.ui.screen.goals.list.GoalsListAdapter
import soutvoid.com.personalwallet.ui.screen.main.IToolbarAdapter
import soutvoid.com.personalwallet.util.replaceWith

class GoalsFragment : BaseFragment(), GoalsView, IToolbarAdapter {

    companion object {
        fun newInstance(): GoalsFragment = GoalsFragment()
    }

    @BindView(R.id.goals_list)
    lateinit var goalsList: RecyclerView
    private var goalsListAdapter: GoalsListAdapter = GoalsListAdapter()

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mGoalsPresenter: GoalsPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): GoalsPresenter =
            GoalsPresenter(App.instance.kodein)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

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
        goalsListAdapter.goals.replaceWith(goals)
        goalsListAdapter.notifyDataSetChanged()
    }

    override fun removeGoal(goalLocalId: Long) {
        val index = goalsListAdapter.goals.indexOfFirst { it.localId == goalLocalId }
        if (index != -1) {
            goalsListAdapter.goals.removeAt(index)
            goalsListAdapter.notifyItemRemoved(index)
        }
    }

    override fun updateGoal(goal: Goal) {
        val index = goalsListAdapter.goals.indexOfFirst { it.localId == goal.localId }
        if (index != -1) {
            goalsListAdapter.goals.set(index, goal)
            goalsListAdapter.notifyItemChanged(index)
        }
    }

    private fun initViews() {
        goalsList.adapter = goalsListAdapter
        goalsList.layoutManager = LinearLayoutManager(context)
        goalsListAdapter.onItemClickListener = {
            mGoalsPresenter.onGoalClick(goalsListAdapter.goals[it])
        }
    }
}