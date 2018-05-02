package soutvoid.com.personalwallet.ui.screen.goals

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.interactor.goal.local.IGoalRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter

@InjectViewState
class GoalsPresenter(kodein: Kodein) : BasePresenter<GoalsView>(kodein) {

    private val goalRepository: IGoalRepository by instance()

    fun onAddGoal() {
        viewState?.openGoalScreen(null)
    }

}