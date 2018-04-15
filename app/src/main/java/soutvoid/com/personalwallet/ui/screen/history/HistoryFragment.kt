package soutvoid.com.personalwallet.ui.screen.history

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
import soutvoid.com.personalwallet.ui.screen.categories.CategoriesActivity
import soutvoid.com.personalwallet.ui.screen.history.adapter.AddEntrySpeedDialAdapter
import soutvoid.com.personalwallet.ui.screen.history.adapter.SectionsListAdapter
import soutvoid.com.personalwallet.ui.screen.history.adapter.TransactionsListAdapter
import soutvoid.com.personalwallet.ui.screen.history.data.SectionData
import uk.co.markormesher.android_fab.FloatingActionButton

class HistoryFragment : BaseFragment(), HistoryView {
    companion object {
        const val TAG = "HistoryFragment"

        fun newInstance(): HistoryFragment = HistoryFragment()
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mHistoryPresenter: HistoryPresenter

    @BindView(R.id.main_add_entry_fab)
    lateinit var addEntryFab: FloatingActionButton
    @BindView(R.id.main_transactions_list)
    lateinit var transactionsList: RecyclerView
    @BindView(R.id.main_sections_list)
    lateinit var sectionsList: RecyclerView

    private lateinit var transactionsListAdapter: TransactionsListAdapter
    private lateinit var sectionsListAdapter: SectionsListAdapter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): HistoryPresenter =
            HistoryPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFabSpeedDial(view.context)
        initTransactionsList(view)
        initSectionsList()
    }

    private fun initTransactionsList(view: View) {
        transactionsList.isNestedScrollingEnabled = false
        transactionsList.layoutManager = LinearLayoutManager(context)
        transactionsListAdapter = TransactionsListAdapter(view.context) { section, posInSection ->
            mHistoryPresenter.onEntryClicked(section, posInSection)
        }
        transactionsList.adapter = transactionsListAdapter
    }

    private fun initFabSpeedDial(context: Context) {
        addEntryFab.speedDialMenuAdapter = AddEntrySpeedDialAdapter(context) { entryType ->
            mHistoryPresenter.onAddEntry(entryType)
        }
    }

    private fun initSectionsList() {
        sectionsListAdapter = SectionsListAdapter(SectionData.values().asList()) { pos, _ ->
            mHistoryPresenter.onSectionClicked(sectionsListAdapter.sections[pos])
        }
        sectionsList.adapter = sectionsListAdapter
        sectionsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun openAddEntry(entryType: EntryType, transactionEntry: TransactionEntry?) {
        context?.let {
            startActivity(AddEntryActivity.getIntent(it, entryType, transactionEntry?.localId))
        }
    }

    override fun showTransactions(transactions: List<Pair<Long, List<TransactionEntry>>>) {
        transactionsListAdapter.transactions = transactions
        transactionsListAdapter.notifyDataSetChanged()
    }

    override fun openCategories() {
        context?.let {
            it.startActivity(CategoriesActivity.getIntent(it))
        }
    }

    override fun openReports() {

    }

    override fun openLimits() {

    }


}
