package soutvoid.com.personalwallet.ui.screen.addentry

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.orhanobut.logger.Logger
import io.realm.Realm
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.transactionentry.ICategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.ITransactionEntryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.screen.addentry.data.NewEntryData

@InjectViewState
class AddEntryPresenter(kodein: Kodein,
                        private val entryType: EntryType)
    : BasePresenter<AddEntryView>(kodein) {

    val realm: Realm by with(this).instance()
    val categoryRepository: ICategoryRepository by with(this).instance()
    val transactionEntryRepository: ITransactionEntryRepository by with(this).instance()
    var categories: List<Category> = listOf()
    var categoryToChoose: String? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.setToolbarColorForEntryType(entryType)
        viewState?.setStatusBarColorForEntryType(entryType)
        viewState?.setTitleForEntryType(entryType)
    }

    override fun attachView(view: AddEntryView?) {
        super.attachView(view)
        loadCategories()
    }

    private fun loadCategories() {
        val categoriesFlowable = categoryRepository.getAll().doOnNext {
            Logger.d(it)
            viewState?.setAvailableCategories(it)
            categoryToChoose?.let {
                viewState?.chooseCategory(it)
                categoryToChoose = null
            }
        }
        this subscribeTo categoriesFlowable
    }

    fun onNewCategoryEntered(name: CharSequence) {
        if (categories.none { it.name == name }) {
            categoryToChoose = name.toString()
            categoryRepository.create(Category(name.toString()))
        }
    }

    fun onSaveClicked(data: NewEntryData) {
        if (validate(data)) {
            val transactionEntry = TransactionEntry(entryType.getName(),
                    data.name, data.category, data.dateAndTimeMillis / 1000,
                    data.moneyValue.toLong(), "")
            transactionEntryRepository.create(transactionEntry)
            viewState?.finish()
        }
    }

    private fun validate(data: NewEntryData): Boolean {
        val nameValid = !data.name.isBlank()
        viewState?.showBlankNameError(!nameValid)
        val moneyValue = data.moneyValue.toLongOrNull()
        val valueValid = moneyValue != null && moneyValue >= 0
        viewState?.showInvalidValueError(!valueValid)
        return nameValid and valueValid
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}
