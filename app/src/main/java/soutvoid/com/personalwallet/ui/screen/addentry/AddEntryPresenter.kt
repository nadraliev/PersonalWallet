package soutvoid.com.personalwallet.ui.screen.addentry

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.orhanobut.logger.Logger
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.transactionentry.local.ICategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.ITransactionEntryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.screen.addentry.data.NewEntryData

@InjectViewState
class AddEntryPresenter(kodein: Kodein,
                        private val entryType: EntryType,
                        private val transactionEntryId: Long? = null)
    : BasePresenter<AddEntryView>(kodein) {

    val categoryRepository: ICategoryRepository by instance()
    val transactionEntryRepository: ITransactionEntryRepository by instance()
    var categories: List<Category> = listOf()
    var categoryToChoose: String? = null
    var transactionEntry: TransactionEntry? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.setToolbarColorForEntryType(entryType)
        viewState?.setStatusBarColorForEntryType(entryType)
        viewState?.setTitleForEntryType(entryType)
        transactionEntryId?.let {
            transactionEntry = transactionEntryRepository.getById(transactionEntryId).blockingFirst()
        }
        transactionEntry?.also {
            viewState?.setName(it.name)
            viewState?.setCategoryName(it.category?.name ?: "")
            viewState?.setDate(it.creationDateSeconds)
            viewState?.setValue(it.moneyValue)
        }
    }

    override fun attachView(view: AddEntryView?) {
        super.attachView(view)
        loadCategories()
    }

    private fun loadCategories() {
        val categoriesObservable = categoryRepository.getAll().doOnNext {
            Logger.d(it)
            categories = it.toList()
            viewState?.setAvailableCategories(it.toList())
            categoryToChoose?.let {
                viewState?.chooseCategory(it)
                categoryToChoose = null
            }
        }
        this subscribeTo categoriesObservable
    }

    fun onNewCategoryEntered(name: CharSequence) {
        if (categories.none { it.name == name.toString() }) {
            categoryToChoose = name.toString()
            this subscribeTo categoryRepository.create(Category(name.toString()))
            loadCategories()
        }
    }

    fun onSaveClicked(data: NewEntryData) {
        if (validate(data)) {
            val category = categories.first { it.name == data.categoryName }
            val newTransactionEntry = TransactionEntry(entryType.getName(),
                    data.name, category, data.dateAndTimeMillis / 1000,
                    data.moneyValue.toLong(), "")
            if (transactionEntry == null) {
                this subscribeTo transactionEntryRepository.create(newTransactionEntry)
            } else {
                transactionEntry?.let { transactionEntry ->
                    newTransactionEntry.id = transactionEntry.id
                    this subscribeTo transactionEntryRepository.update(newTransactionEntry)
                }
            }
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
}
