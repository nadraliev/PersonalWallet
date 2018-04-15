package soutvoid.com.personalwallet.ui.screen.history.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import org.jetbrains.anko.textColor
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.Income
import soutvoid.com.personalwallet.domain.transactionentry.Outcome
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.ui.common.recycler.SimpleSectionedAdapter
import soutvoid.com.personalwallet.ui.util.getCurrencySymbol
import soutvoid.com.personalwallet.ui.util.getHumanReadableDateShort
import soutvoid.com.personalwallet.ui.util.inflate

/**
 * Created by andrew on 23.03.18.
 */
class TransactionsListAdapter(private val context: Context,
                              var onItemClickListener: ((Int, Int) -> Unit)? = null)
    : SimpleSectionedAdapter<TransactionsListAdapter.TransactionViewHolder>() {

    var transactions: List<Pair<Long, List<TransactionEntry>>> = listOf()

    override fun getSectionHeaderTitle(section: Int): String =
            transactions[section].first.getHumanReadableDateShort(context)

    override fun getSectionCount(): Int =
            transactions.size

    override fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): TransactionViewHolder? {
        val view = parent?.inflate(R.layout.item_transaction_main)
        return view?.let {
            TransactionViewHolder(it)
        }
    }

    override fun getItemCountForSection(section: Int): Int =
            transactions[section].second.size

    override fun onBindItemViewHolder(holder: TransactionViewHolder?, section: Int, position: Int) {
        holder?.bind(transactions[section].second[position])
    }

    inner class TransactionViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        @BindView(R.id.item_transaction_name)
        lateinit var nameTv: TextView
        @BindView(R.id.item_transaction_category)
        lateinit var categoryTv: TextView
        @BindView(R.id.item_transaction_value)
        lateinit var valueTv: TextView
        @BindView(R.id.item_transaction_type_icon)
        lateinit var typeIcon: ImageView

        private var incomeColor: Int
        private var outcomeColor: Int
        private var noCategoryMsg: String

        init {
            ButterKnife.bind(this, view)
            incomeColor = ContextCompat.getColor(view.context, R.color.colorEntryIncome)
            outcomeColor = ContextCompat.getColor(view.context, R.color.colorEntryOutcome)
            noCategoryMsg = view.context.getString(R.string.main_transactions_list_no_category)
            itemView.setOnClickListener {
                onItemClickListener?.invoke(sectionForPosition[adapterPosition], positionWithinSection[adapterPosition])
            }
        }

        fun bind(transactionEntry: TransactionEntry) {
            with(transactionEntry) {
                nameTv.text = name
                categoryTv.text = category?.name ?: noCategoryMsg
                valueTv.text = "$moneyValue ${getCurrencySymbol()}"
                valueTv.textColor = when (getEntryType()) {
                    is Income -> incomeColor
                    is Outcome -> outcomeColor
                    null -> outcomeColor
                }
                typeIcon.setImageResource(when (getEntryType()) {
                    Income -> R.drawable.ic_income
                    Outcome -> R.drawable.ic_outcome
                    null -> R.drawable.ic_outcome
                })
            }
        }

    }

}