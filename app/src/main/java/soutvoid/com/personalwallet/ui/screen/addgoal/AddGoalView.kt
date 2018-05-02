package soutvoid.com.personalwallet.ui.screen.addgoal

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.ui.base.BaseView

interface AddGoalView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun finish()

    fun fillFieldsForEdit(goal: Goal)
}