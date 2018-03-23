package soutvoid.com.personalwallet.ui.screen.addentry.widget

import android.content.Context
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.Category

/**
 * Created by andrew on 18.03.18.
 */
class ChooseCategorySectionView : ConstraintLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @BindView(R.id.add_entry_category_tv)
    lateinit var categoryTv: TextView

    var onNewCategoryInputListener: ((CharSequence) -> Unit)? = null

    var categories: List<Category> = listOf()
        set(value) {
            field = value.sortedBy { it.name }
            maybeUpdateDefaultCategory()
        }

    var currentCategory: Category? by serialState()

    init {
        View.inflate(context, R.layout.view_choose_category_section, this)
        ButterKnife.bind(this, rootView)
    }

    fun selectCategory(position: Int) {
        currentCategory = categories[position]
        categoryTv.text = currentCategory?.name
    }

    fun selectCategory(name: String) {
        val index = categories.indexOfFirst { it.name == name }
        if (index != -1)
            selectCategory(index)
    }

    private fun maybeUpdateDefaultCategory() {
        if (currentCategory == null && categories.isNotEmpty()) {
            selectCategory(0)
        } else {
            currentCategory?.let { categoryTv.text = it.name }
        }
    }

    @OnClick(R.id.add_entry_category_tv)
    internal fun onCategoryTvClick(v: View) {
        showCategoriesDialog()
    }

    private fun showCategoriesDialog() {
        val entries = categories.map { it.name }.toMutableList()
        entries.add(rootView.context.getString(R.string.add_entry_add_category_option))
        MaterialDialog.Builder(rootView.context)
                .items(entries)
                .itemsCallback { dialog, _, position, _ ->
                    onCategoryOptionChosen(dialog, position)
                }.show()
    }

    private fun onCategoryOptionChosen(dialog: MaterialDialog, position: Int) {
        dialog.dismiss()
        if (position == categories.size)
            showNewCategoryDialog()
        else
            selectCategory(position)
    }

    private fun showNewCategoryDialog() {
        MaterialDialog.Builder(rootView.context)
                .input(R.string.add_entry_new_category_hint, 0, false) { dialog, input ->
                    dialog.dismiss()
                    onNewCategoryInputListener?.invoke(input)
                }.show()
    }

    override fun onSaveInstanceState(): Parcelable? =
            freezeInstanceState(super.onSaveInstanceState())

    override fun onRestoreInstanceState(state: Parcelable) {
        super.onRestoreInstanceState(unfreezeInstanceState(state))
    }
}