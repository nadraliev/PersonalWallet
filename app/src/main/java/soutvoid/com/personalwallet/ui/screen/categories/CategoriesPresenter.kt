package soutvoid.com.personalwallet.ui.screen.categories

import com.arellomobile.mvp.InjectViewState
import com.birbit.android.jobqueue.JobManager
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.transactionentry.local.ICategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.local.ITransactionEntryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.server.AddCategoryJob
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.util.hasNoIndex

@InjectViewState
class CategoriesPresenter(kodein: Kodein) : BasePresenter<CategoriesView>(kodein) {

    private val categoriesRepository: ICategoryRepository by instance()
    private val transactionEntryRepository: ITransactionEntryRepository by instance()
    private val jobManager: JobManager by instance()

    private var categories = mutableListOf<Category>()

    override fun attachView(view: CategoriesView?) {
        super.attachView(view)
        loadCategories()
        jobManager.addJobInBackground(AddCategoryJob(Category(name = "name")))
    }

    private fun loadCategories() {
        val categoriesObservable = categoriesRepository.getAll().take(1).doOnNext {
            categories = it.toMutableList()
            viewState?.showCategories(categories)
        }
        this subscribeTo categoriesObservable
    }

    fun onCategoryDelete(position: Int) {
        if (categories hasNoIndex position) return
        val categoryToDelete = categories[position]
        val deleteCategoryObservable = transactionEntryRepository.getAll().take(1).doOnNext { transactions ->
            transactions.filter { it.category?.localId == categoryToDelete.localId }.forEach {
                transactionEntryRepository.delete(it.localId)
            }
            this subscribeTo categoriesRepository.delete(categoryToDelete.localId)
            categories.removeAt(position)
            viewState?.removeCategory(position)
        }
        this subscribeTo deleteCategoryObservable
    }

    fun onCategoryEdit(position: Int) {
        if (categories hasNoIndex position) return
        val categoryToEdit = categories[position]
        viewState?.showEnterCategoryNameDialog(categoryToEdit.name) { newName ->
            doChangeCategoryName(categoryToEdit, newName)
            viewState?.updateCategory(position, categoryToEdit)
        }
    }

    private fun doChangeCategoryName(category: Category, newName: String) {
        category.name = newName
        this subscribeTo categoriesRepository.update(category)
    }

    fun onAddCategory() {
        viewState?.showNewCategoryNameDialog { name ->
            val category = doAddCategory(name)
            viewState?.addCategory(categories.size - 1, category)
        }
    }

    private fun doAddCategory(name: String): Category {
        val newCategory = Category(name)
        this subscribeTo categoriesRepository.create(newCategory)
        categories.add(newCategory)
        return newCategory
    }
}