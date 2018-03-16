package soutvoid.com.personalwallet.ui.util

import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.Income
import soutvoid.com.personalwallet.domain.transactionentry.Outcome

/**
 * Created by andrew on 16.03.18.
 */

fun EntryType.getColorResId() = when (this) {
    is Income -> R.color.colorEntryIncome
    is Outcome -> R.color.colorEntryOutcome
}

fun EntryType.getDarkColorResId() = when (this) {
    is Income -> R.color.colorEntryIncomeDark
    is Outcome -> R.color.colorEntryOutcomeDark
}

fun EntryType.getIconResId() = when (this) {
    is Income -> R.drawable.ic_arrow_downward_light
    is Outcome -> R.drawable.ic_arrow_upward_light
}

fun EntryType.getNameResId() = when (this) {
    is Income -> R.string.common_entry_type_name_income
    is Outcome -> R.string.common_entry_type_name_outcome
}
