package soutvoid.com.personalwallet.ui.screen.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.ui.util.getHumanReadableDateShort

/**
 * Created by andrew on 23.03.18.
 */
class TransactionsListAdapter(private val context: Context) : SimpleSectionedAdapter<TransactionsListAdapter.TransactionViewHolder>() {

    var transactions: List<Pair<Long, List<TransactionEntry>>> = listOf()

    override fun getSectionHeaderTitle(section: Int): String =
            transactions[section].first.getHumanReadableDateShort(context)

    override fun getSectionCount(): Int =
            transactions.size

    override fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): TransactionViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_transaction_main, parent, false)
        return view?.let {
            TransactionViewHolder(it)
        }
    }

    override fun getItemCountForSection(section: Int): Int =
            transactions[section].second.size


    override fun onBindItemViewHolder(holder: TransactionViewHolder?, section: Int, position: Int) {
        holder?.bind(transactions[section].second[position])
    }

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.item_transaction_name)
        lateinit var nameTv: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(transactionEntry: TransactionEntry) {
            nameTv.text = transactionEntry.name
        }

    }

}