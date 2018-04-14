package soutvoid.com.personalwallet.domain.transactionentry

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import soutvoid.com.personalwallet.domain.IBaseEntity
import java.io.Serializable

/**
 * Created by andrew on 16.03.18.
 */
open class Category(var name: String = "",
                    @PrimaryKey override var localId: Long = 0,
                    var id: Long = 0) : RealmObject(), Serializable, IBaseEntity