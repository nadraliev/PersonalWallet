package soutvoid.com.personalwallet.ui.screen.categories

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import io.realm.Realm
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.transactionentry.ICategoryRepository
import soutvoid.com.personalwallet.interactor.transactionentry.ITransactionEntryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.util.hasNoIndex

@InjectViewState
class CategoriesPresenter(kodein: Kodein) : BasePresenter<CategoriesView>(kodein) {

    private val realm: Realm by with(this).instance()
    private val categoriesRepository: ICategoryRepository by with(this).instance()
    private val transactionEntryRepository: ITransactionEntryRepository by with(this).instance()

    private var categories = mutableListOf<Category>()

    override fun attachView(view: CategoriesView?) {
        super.attachView(view)
        loadCategories()
    }

    private fun loadCategories() {
        val categoriesFlowable = categoriesRepository.getAll().take(1).doOnNext {
            categories = it.toMutableList()
            viewState?.showCategories(categories)
        }
        this subscribeTo categoriesFlowable
    }

    fun onCategoryDelete(position: Int) {
        if (categories hasNoIndex position) return
        val categoryToDelete = categories[position]
        val deleteCategoryFlowable = transactionEntryRepository.getAll().take(1).doOnNext { transactions ->
            realm.executeTransaction {
                transactions.filter { it.category?.id == categoryToDelete.id }.forEach {
                    transactionEntryRepository.delete(it.id)
                }
                categoriesRepository.delete(categoryToDelete.id)
                categories.removeAt(position)
            }
            viewState?.removeCategory(position)
        }
        this subscribeTo deleteCategoryFlowable
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
        realm.executeTransaction {
            category.name = newName
            categoriesRepository.update(category)
        }
    }

    fun onAddCategory() {
        viewState?.showNewCategoryNameDialog { name ->
            val category = doAddCategory(name)
            viewState?.addCategory(categories.size - 1, category)
        }
    }

    private fun doAddCategory(name: String): Category {
        val newCategory = Category(name)
        realm.executeTransaction {
            categoriesRepository.create(newCategory)
        }
        categories.add(newCategory)
        return newCategory
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}