package soutvoid.com.personalwallet.domain.transactionentry

import io.realm.RealmObject
import java.io.Serializable

/**
 * Created by andrew on 16.03.18.
 */
open class TransactionEntry(var id: String = "",
                            private var entryTypeName: String = "income",
                            var categoryId: Category? = null,
                            var creationDate: Long = System.currentTimeMillis() / 1000,
                            var moneyValue: Long = 0,
                            var comment: String = "") : RealmObject(), Serializable {

    fun getEntryType() = EntryType.getByName(entryTypeName)

    fun setEntryType(value: EntryType) {
        entryTypeName = value.getName()
    }

}