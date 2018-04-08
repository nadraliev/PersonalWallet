package soutvoid.com.personalwallet.ui.screen.main

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.ui.base.BaseView

interface MainView : BaseView {

    @StateStrategyType(value = SkipStrategy::class)
    fun openAddEntry(entryType: EntryType, transactionEntry: TransactionEntry? = null)

    fun showTransactions(transactions: List<Pair<Long, List<TransactionEntry>>>)
}
