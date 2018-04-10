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
                        private val entryType: EntryType,
                        private val transactionEntryId: Long? = null)
    : BasePresenter<AddEntryView>(kodein) {

    val realm: Realm by with(this).instance()
    val categoryRepository: ICategoryRepository by with(this).instance()
    val transactionEntryRepository: ITransactionEntryRepository by with(this).instance()
    var categories: List<Category> = listOf()
    var categoryToChoose: String? = null
    var transactionEntry: TransactionEntry? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState?.setToolbarColorForEntryType(entryType)
        viewState?.setStatusBarColorForEntryType(entryType)
        viewState?.setTitleForEntryType(entryType)
        transactionEntryId?.let {
            transactionEntry = transactionEntryRepository.getById(transactionEntryId)?.blockingFirst()
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
        val categoriesFlowable = categoryRepository.getAll().doOnNext {
            Logger.d(it)
            categories = it
            viewState?.setAvailableCategories(it)
            categoryToChoose?.let {
                viewState?.chooseCategory(it)
                categoryToChoose = null
            }
        }
        this subscribeTo categoriesFlowable
    }

    fun onNewCategoryEntered(name: CharSequence) {
        if (categories.none { it.name == name.toString() }) {
            categoryToChoose = name.toString()
            realm.executeTransaction {
                categoryRepository.create(Category(name.toString()))
            }
        }
    }

    fun onSaveClicked(data: NewEntryData) {
        if (validate(data)) {
            val category = categories.first { it.name == data.categoryName }
            val newTransactionEntry = TransactionEntry(entryType.getName(),
                    data.name, category, data.dateAndTimeMillis / 1000,
                    data.moneyValue.toLong(), "")
            if (transactionEntry == null) {
                realm.executeTransaction {
                    transactionEntryRepository.create(newTransactionEntry)
                }
            } else {
                transactionEntry?.let { transactionEntry ->
                    realm.executeTransaction {
                        newTransactionEntry.id = transactionEntry.id
                        transactionEntryRepository.update(newTransactionEntry)
                    }
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

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}
