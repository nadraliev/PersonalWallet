package soutvoid.com.personalwallet.ui.screen.goals

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.ui.base.BaseView

interface GoalsView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun openGoalScreen(goal: Goal?)

    fun showGoals(goals: List<Goal>)
    fun removeGoal(goalLocalId: Long)
    fun updateGoal(goal: Goal)

}