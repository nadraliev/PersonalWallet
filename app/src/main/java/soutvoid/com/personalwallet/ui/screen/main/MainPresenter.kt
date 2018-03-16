package soutvoid.com.personalwallet.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.ui.base.BasePresenter

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    fun onAddEntry(entryType: EntryType) {
        viewState?.openAddEntry(entryType)
    }

}
