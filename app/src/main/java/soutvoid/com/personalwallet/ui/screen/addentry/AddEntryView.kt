package soutvoid.com.personalwallet.ui.screen.addentry

import soutvoid.com.personalwallet.domain.EntryType
import soutvoid.com.personalwallet.ui.base.BaseView

interface AddEntryView : BaseView {

    fun setToolbarColorForEntryType(entryType: EntryType)

    fun setStatusBarColorForEntryType(entryType: EntryType)

    fun setTitleForEntryType(entryType: EntryType)

}
