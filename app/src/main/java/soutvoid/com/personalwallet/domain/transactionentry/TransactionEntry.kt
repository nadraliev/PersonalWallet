package soutvoid.com.personalwallet.domain.transactionentry

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import soutvoid.com.personalwallet.domain.IBaseEntity
import java.io.Serializable

/**
 * Created by andrew on 16.03.18.
 */
open class TransactionEntry(private var entryTypeName: String = "income",
                            var name: String = "",
                            var category: Category? = null,
                            var creationDateSeconds: Long = System.currentTimeMillis() / 1000,
                            var moneyValue: Long = 0,
                            var comment: String = "",
                            @PrimaryKey override var id: Long = 0) : RealmObject(), Serializable, IBaseEntity {

    fun getEntryType() = EntryType.getByName(entryTypeName)!!

    fun setEntryType(value: EntryType) {
        entryTypeName = value.getName()
    }

}