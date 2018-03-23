package soutvoid.com.personalwallet.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.transactionentry.ITransactionEntryRepository
import soutvoid.com.personalwallet.ui.base.BasePresenter

@InjectViewState
class MainPresenter(kodein: Kodein) : BasePresenter<MainView>(kodein) {

    private val transactionRepository: ITransactionEntryRepository by with(this).instance()
    private var transactions: List<Pair<Long, List<TransactionEntry>>> = listOf()   //entries grouped and ordered by date

    override fun attachView(view: MainView?) {
        super.attachView(view)
        loadTransactions()
    }

    private fun loadTransactions() {
        val getAllFlowable = transactionRepository.getAll()
                .doOnNext {
                    transactions = it.groupBy { it.creationDate }
                            .map { Pair(it.key, it.value) }.sortedByDescending { it.first }
                    viewState?.showTransactions(transactions)
                }
        this subscribeTo getAllFlowable
    }

    fun onAddEntry(entryType: EntryType) {
        viewState?.openAddEntry(entryType)
    }

}
