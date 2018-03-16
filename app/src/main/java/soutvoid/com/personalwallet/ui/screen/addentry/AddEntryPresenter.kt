package soutvoid.com.personalwallet.ui.screen.addentry

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.interactor.transactionentry.ICategoryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter

@InjectViewState
class AddEntryPresenter(kodein: Kodein,
                        private val entryType: EntryType)
    : BasePresenter<AddEntryView>(kodein) {

    val categoryRepository: ICategoryRepository by instance()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.setToolbarColorForEntryType(entryType)
        viewState?.setStatusBarColorForEntryType(entryType)
        viewState?.setTitleForEntryType(entryType)
    }
}
