package soutvoid.com.personalwallet.ui.screen.addgoal.data

import android.support.annotation.PluralsRes
import soutvoid.com.personalwallet.R

enum class ReminderPeriod(val valueSeconds: Long, @PluralsRes val label: Int) {
    DAY(86400, R.plurals.add_goal_reminder_period_day_label),
    WEEK(604800, R.plurals.add_goal_reminder_period_week_label),
    MONTH(18144000, R.plurals.add_goal_reminder_period_month_label),
    YEAR(22075200, R.plurals.add_goal_reminder_period_year_label);

    companion object {
        fun findBySeconds(seconds: Long): ReminderPeriod =
                if (seconds % YEAR.valueSeconds == 0L) YEAR
                else if (seconds % MONTH.valueSeconds == 0L) MONTH
                else if (seconds % WEEK.valueSeconds == 0L) WEEK
                else if (seconds % DAY.valueSeconds == 0L) DAY
                else DAY
    }

    fun getSeconds(quantity: Int): Long = quantity * valueSeconds
}