package soutvoid.com.personalwallet.ui.screen.addentry

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import soutvoid.com.personalwallet.domain.EntryType

@InjectViewState
class AddEntryPresenter(private val entryType: EntryType)
    : MvpPresenter<AddEntryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.setToolbarColorForEntryType(entryType)
        viewState?.setStatusBarColorForEntryType(entryType)
        viewState?.setTitleForEntryType(entryType)
    }
}
