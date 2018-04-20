package soutvoid.com.personalwallet.ui.screen.history.data

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import soutvoid.com.personalwallet.R

/**
 * Created by andrew on 31.03.18.
 */
enum class SectionData(@DrawableRes val iconResId: Int,
                       @StringRes val nameResId: Int) {
    CATEGORIES(R.drawable.ic_tag_multiple_grey, R.string.history_section_categories_name),
    REPORTS(R.drawable.ic_chart_arc_grey, R.string.history_section_reports_name),
    LIMIT(R.drawable.ic_alert_grey, R.string.history_section_limit_name)
}