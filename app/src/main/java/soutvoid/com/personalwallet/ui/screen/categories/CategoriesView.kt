package soutvoid.com.personalwallet.ui.screen.categories

import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.ui.base.BaseView

interface CategoriesView : BaseView {

    fun showCategories(categories: List<Category>)

    fun removeCategory(position: Int)

    fun updateCategory(position: Int, newCategory: Category)

    fun showEnterCategoryNameDialog(prefill: String, onNameEnter: (String) -> Unit)

    fun showNewCategoryNameDialog(onNameEnter: (String) -> Unit)

    fun addCategory(position: Int, newCategory: Category)

}