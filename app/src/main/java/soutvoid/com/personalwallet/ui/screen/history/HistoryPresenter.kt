package soutvoid.com.personalwallet.ui.screen.history

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.transactionentry.local.ITransactionEntryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.screen.history.data.SectionData
import soutvoid.com.personalwallet.util.floorTo
import java.util.concurrent.TimeUnit

@InjectViewState
class HistoryPresenter(kodein: Kodein) : BasePresenter<HistoryView>(kodein) {

    private val transactionRepository: ITransactionEntryRepository by instance()
    private var groupedTransactions: List<Pair<Long, List<TransactionEntry>>> = listOf()   //entries grouped and ordered by day

    override fun attachView(view: HistoryView?) {
        super.attachView(view)
        loadTransactions()
    }

    private fun loadTransactions() {
        val getAllFlowable = transactionRepository.getAll()
                .doOnNext {
                    groupedTransactions = it.groupBy { it.creationDateSeconds floorTo TimeUnit.DAYS.toSeconds(1) }
                            .map { Pair(it.key, it.value) }.sortedByDescending { it.first }
                    viewState?.showTransactions(groupedTransactions)
                }
        getAllFlowable.subscribeTo()
    }

    fun onAddEntry(entryType: EntryType) {
        viewState?.openAddEntry(entryType)
    }

    fun onEntryClicked(section: Int, posInSection: Int) {
        with(groupedTransactions[section].second[posInSection]) {
            viewState?.openAddEntry(getEntryType(), this)
        }
    }

    fun onSectionClicked(section: SectionData) {
        when (section) {
            SectionData.CATEGORIES -> viewState?.openCategories()
            SectionData.REPORTS -> viewState?.openReports()
            SectionData.LIMIT -> viewState?.openLimits()
        }
    }

}
