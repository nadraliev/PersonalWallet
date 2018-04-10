package soutvoid.com.personalwallet.ui.screen.categories.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.ui.util.inflate

class CategoriesListAdapter(var categories: MutableList<Category> = mutableListOf(),
                            var onEditClick: ((Int) -> Unit)? = null,
                            var onDeleteClick: ((Int) -> Unit)? = null)
    : RecyclerView.Adapter<CategoriesListAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = parent.inflate(R.layout.item_category)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.category_item_name)
        lateinit var nameTv: TextView
        @BindView(R.id.category_item_delete)
        lateinit var deleteBtn: View
        @BindView(R.id.category_item_edit)
        lateinit var editBtn: View

        init {
            ButterKnife.bind(this, view)
            deleteBtn.setOnClickListener { onDeleteClick?.invoke(adapterPosition) }
            editBtn.setOnClickListener { onEditClick?.invoke(adapterPosition) }
        }

        fun bind(category: Category) {
            nameTv.text = category.name
        }

    }

}