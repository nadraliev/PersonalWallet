package soutvoid.com.personalwallet.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.transactionentry.ITransactionEntryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter
import soutvoid.com.personalwallet.ui.screen.main.data.SectionData
import soutvoid.com.personalwallet.util.floorTo
import java.util.concurrent.TimeUnit

@InjectViewState
class MainPresenter(kodein: Kodein) : BasePresenter<MainView>(kodein) {

    private val transactionRepository: ITransactionEntryRepository by with(this).instance()
    private var groupedTransactions: List<Pair<Long, List<TransactionEntry>>> = listOf()   //entries grouped and ordered by day

    override fun attachView(view: MainView?) {
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
        this subscribeTo getAllFlowable
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
