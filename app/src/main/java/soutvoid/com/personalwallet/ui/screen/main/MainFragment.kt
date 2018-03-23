package soutvoid.com.personalwallet.ui.screen.main

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.screen.addentry.AddEntryActivity
import soutvoid.com.personalwallet.ui.screen.main.adapter.AddEntrySpeedDialAdapter
import soutvoid.com.personalwallet.ui.screen.main.adapter.TransactionsListAdapter
import uk.co.markormesher.android_fab.FloatingActionButton

class MainFragment : BaseFragment(), MainView {
    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment = MainFragment()
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mMainPresenter: MainPresenter

    @BindView(R.id.main_add_entry_fab)
    lateinit var addEntryFab: FloatingActionButton
    @BindView(R.id.main_transactions_list)
    lateinit var transactionsList: RecyclerView

    private lateinit var transactionsListAdapter: TransactionsListAdapter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): MainPresenter =
            MainPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFabSpeedDial(view.context)
        initTransactionsList(view)
    }

    private fun initTransactionsList(view: View) {
        transactionsList.layoutManager = LinearLayoutManager(context)
        transactionsListAdapter = TransactionsListAdapter(view.context)
        transactionsList.adapter = transactionsListAdapter
    }

    private fun initFabSpeedDial(context: Context) {
        addEntryFab.speedDialMenuAdapter = AddEntrySpeedDialAdapter(context) { entryType ->
            mMainPresenter.onAddEntry(entryType)
        }
    }

    override fun openAddEntry(entryType: EntryType) {
        context?.let {
            startActivity(AddEntryActivity.getIntent(it, entryType))
        }
    }

    override fun showTransactions(transactions: List<Pair<Long, List<TransactionEntry>>>) {
        transactionsListAdapter.transactions = transactions
        transactionsListAdapter.notifyDataSetChanged()
    }
}
