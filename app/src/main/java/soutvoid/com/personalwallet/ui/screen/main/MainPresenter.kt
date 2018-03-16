package soutvoid.com.personalwallet.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import soutvoid.com.personalwallet.domain.EntryType

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    fun onAddEntry(entryType: EntryType) {
        viewState?.openAddEntry(entryType)
    }

}
