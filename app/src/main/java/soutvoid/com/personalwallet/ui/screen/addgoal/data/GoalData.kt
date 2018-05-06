package soutvoid.com.personalwallet.ui.screen.addgoal.data

data class GoalData(
        var targetValue: String,
        var dateMillis: Long,
        var name: String,
        var reminderValue: String,
        var reminderPeriod: ReminderPeriod
)