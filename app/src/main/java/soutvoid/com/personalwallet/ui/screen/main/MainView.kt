package soutvoid.com.personalwallet.ui.screen.main

import soutvoid.com.personalwallet.domain.EntryType
import soutvoid.com.personalwallet.ui.base.BaseView

interface MainView : BaseView {

    fun openAddEntry(entryType: EntryType)
}
