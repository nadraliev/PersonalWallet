package soutvoid.com.personalwallet.ui.screen.goals

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.interactor.goal.local.IGoalRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.util.replaceWith

@InjectViewState
class GoalsPresenter(kodein: Kodein) : BasePresenter<GoalsView>(kodein) {

    private val goalRepository: IGoalRepository by instance()
    private val goals: MutableList<Goal> = mutableListOf()

    override fun attachView(view: GoalsView?) {
        super.attachView(view)
        loadGoals()
    }

    fun onAddGoal() {
        viewState?.openGoalScreen(null)
    }

    fun onGoalClick(goal: Goal) {
        viewState?.openGoalScreen(goal)
    }

    private fun loadGoals() {
        goalRepository.getAll().subscribeAsyncTo(
                onNext = {
                    goals.replaceWith(it)
                    viewState?.showGoals(goals)
                }
        )
    }

}