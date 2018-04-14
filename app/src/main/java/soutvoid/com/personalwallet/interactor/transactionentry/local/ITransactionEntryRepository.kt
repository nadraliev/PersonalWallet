package soutvoid.com.personalwallet.interactor.transactionentry.local

import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.IBaseRepository

/**
 * Created by andrew on 23.03.18.
 */
interface ITransactionEntryRepository : IBaseRepository<Long, TransactionEntry>