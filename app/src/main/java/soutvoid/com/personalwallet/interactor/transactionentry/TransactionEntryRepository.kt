package soutvoid.com.personalwallet.interactor.transactionentry

import io.realm.Realm
import soutvoid.com.personalwallet.domain.transactionentry.TransactionEntry
import soutvoid.com.personalwallet.interactor.BaseRepository

/**
 * Created by andrew on 23.03.18.
 */
class TransactionEntryRepository(dbHelper: Realm)
    : BaseRepository<TransactionEntry>(dbHelper, TransactionEntry::class.java), ITransactionEntryRepository