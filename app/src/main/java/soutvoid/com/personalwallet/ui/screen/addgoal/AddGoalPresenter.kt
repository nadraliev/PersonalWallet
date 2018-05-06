package soutvoid.com.personalwallet.ui.screen.addgoal

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.interactor.goal.local.IGoalRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.screen.addgoal.data.GoalData

@InjectViewState
class AddGoalPresenter(kodein: Kodein, val goalLocalId: Long? = 0)
    : BasePresenter<AddGoalView>(kodein) {

    private val goalRepository: IGoalRepository by instance()
    private var existingGoal: Goal? = null

    override fun attachView(view: AddGoalView?) {
        super.attachView(view)
        if (goalLocalId != null) {
            loadExistingGoal()
        }
    }

    fun save(data: GoalData) {
        if (validate(data)) {
            doSave(data)
            viewState?.finish()
        }
    }

    private fun loadExistingGoal() {
        goalLocalId?.let {
            existingGoal = goalRepository.getById(goalLocalId).blockingFirst()
            existingGoal?.let {
                viewState?.fillFieldsForEdit(it)
            }
        }
    }

    private fun validate(data: GoalData): Boolean {
        var result = true
        val reminderValue = data.reminderValue.toIntOrNull()
        if (reminderValue == null || reminderValue <= 0) {
            viewState?.showInvalidReminderValueError()
            result = false
        }
        val targetValue = data.targetValue.toIntOrNull()
        if (targetValue == null || targetValue <= 0) {
            viewState?.showInvalidTargetValueError()
            result = false
        }
        return result
    }

    private fun doSave(data: GoalData) {
        val goal = convertData(data)
        if (goalLocalId != null)
            doUpdateExistingGoal(goal)
        else
            doCreateNewGoal(goal)
    }

    private fun convertData(data: GoalData): Goal =
            Goal(
                    data.name, data.targetValue.toLong(), 0L,
                    data.dateMillis / 1000, "",
                    data.reminderPeriod.getSeconds(data.reminderValue.toInt())
            )

    private fun doCreateNewGoal(data: Goal) {
        goalRepository.create(data).subscribe()
    }

    private fun doUpdateExistingGoal(data: Goal) {
        existingGoal?.apply {
            name = data.name
            targetMoneyValue = data.targetMoneyValue
            period = data.period
            reminedIntervalSec = data.reminedIntervalSec
            goalRepository.update(this)
        }
    }

}