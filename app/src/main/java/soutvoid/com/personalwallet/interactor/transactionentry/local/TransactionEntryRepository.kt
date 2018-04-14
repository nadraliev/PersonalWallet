package soutvoid.com.personalwallet.interactor.transactionentry.local

import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.BaseRepository

/**
 * Created by andrew on 23.03.18.
 */
class TransactionEntryRepository
    : BaseRepository<TransactionEntry>(TransactionEntry::class.java), ITransactionEntryRepository