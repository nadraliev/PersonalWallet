package soutvoid.com.personalwallet.domain.goal

import io.realm.RealmObject
import soutvoid.com.personalwallet.domain.IBaseEntity
import java.io.Serializable

open class Goal(
        var name: String = "",
        var moneyValue: Long = 0,
        var period: Long = 0,
        var description: String = "",
        var reminedIntervalSec: Long = 0,
        var id: Long = 0,
        override var localId: Long = 0
) : Serializable, IBaseEntity, RealmObject()