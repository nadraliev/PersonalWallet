package soutvoid.com.personalwallet.ui.screen.addentry

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.orhanobut.logger.Logger
import io.realm.Realm
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.interactor.transactionentry.ICategoryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter

@InjectViewState
class AddEntryPresenter(kodein: Kodein,
                        private val entryType: EntryType)
    : BasePresenter<AddEntryView>(kodein) {

    val realm: Realm by with(this).instance()
    val categoryRepository: ICategoryRepository by with(this).instance()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.setToolbarColorForEntryType(entryType)
        viewState?.setStatusBarColorForEntryType(entryType)
        viewState?.setTitleForEntryType(entryType)
        loadCategories()
    }

    private fun loadCategories() {
        Logger.d(categoryRepository.getAll())
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}
