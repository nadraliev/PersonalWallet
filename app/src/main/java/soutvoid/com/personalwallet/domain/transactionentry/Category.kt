package soutvoid.com.personalwallet.domain.transactionentry

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

/**
 * Created by andrew on 16.03.18.
 */
open class Category(@PrimaryKey var id: String = "",
                    var name: String = "") : RealmObject(), Serializable