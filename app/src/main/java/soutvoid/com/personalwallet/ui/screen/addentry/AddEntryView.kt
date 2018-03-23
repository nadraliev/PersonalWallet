package soutvoid.com.personalwallet.ui.screen.addentry

import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.ui.base.BaseView

interface AddEntryView : BaseView {

    fun setToolbarColorForEntryType(entryType: EntryType)

    fun setStatusBarColorForEntryType(entryType: EntryType)

    fun setTitleForEntryType(entryType: EntryType)

    fun setAvailableCategories(categories: List<Category>)

    fun chooseCategory(name: String)

    fun finish()

    fun showBlankNameError(show: Boolean)

    fun showInvalidValueError(show: Boolean)

}
