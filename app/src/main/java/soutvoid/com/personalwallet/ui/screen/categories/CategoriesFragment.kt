package soutvoid.com.personalwallet.ui.screen.categories

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.screen.categories.list.CategoriesListAdapter

class CategoriesFragment : BaseFragment(), CategoriesView {

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var presenter: CategoriesPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): CategoriesPresenter = CategoriesPresenter(App.instance.kodein)

    @BindView(R.id.categories_list)
    lateinit var categoriesList: RecyclerView
    private var categoriesListAdapter = CategoriesListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoriesList()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_categories

    private fun initCategoriesList() {
        categoriesList.adapter = categoriesListAdapter
        categoriesList.layoutManager = LinearLayoutManager(context)
        categoriesListAdapter.onDeleteClick = { presenter.onCategoryDelete(it) }
        categoriesListAdapter.onEditClick = { presenter.onCategoryEdit(it) }
    }

    override fun showCategories(categories: List<Category>) {
        categoriesListAdapter.categories.clear()
        categoriesListAdapter.categories.addAll(categories)
        categoriesListAdapter.notifyDataSetChanged()
    }

    override fun removeCategory(position: Int) {
        categoriesListAdapter.categories.removeAt(position)
        categoriesListAdapter.notifyItemRemoved(position)
    }

    override fun updateCategory(position: Int, newCategory: Category) {
        categoriesListAdapter.categories[position] = newCategory
        categoriesListAdapter.notifyItemChanged(position)
    }

    override fun showEnterCategoryNameDialog(prefill: String, onNameEnter: (String) -> Unit) {
        context?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.categories_edit_name_dialog_title)
                    .input(it.getString(R.string.categories_edit_name_dialog_hint),
                            prefill, false) { _, newName ->
                        onNameEnter(newName.toString())
                    }.show()
        }
    }

    @OnClick(R.id.categories_add)
    fun onAddCategoryClick(view: View) {
        presenter.onAddCategory()
    }

    override fun showNewCategoryNameDialog(onNameEnter: (String) -> Unit) {
        context?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.categories_new_category_dialog_title)
                    .input(it.getString(R.string.categories_new_category_dialog_name_hint),
                            null, false) { _, name ->
                        onNameEnter(name.toString())
                    }.show()
        }
    }

    override fun addCategory(position: Int, newCategory: Category) {
        categoriesListAdapter.categories.add(position, newCategory)
        categoriesListAdapter.notifyItemInserted(position)
    }
}