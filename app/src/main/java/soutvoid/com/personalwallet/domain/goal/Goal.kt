package soutvoid.com.personalwallet.domain.goal

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import soutvoid.com.personalwallet.domain.IBaseEntity
import java.io.Serializable

open class Goal(
        var name: String = "",
        var targetMoneyValue: Long = 0,
        var currentMoneyValue: Long = 0,
        var period: Long = 0,
        var description: String = "",
        var reminedIntervalSec: Long = 0,
        var id: Long = 0,
        @PrimaryKey
        override var localId: Long = 0
) : Serializable, IBaseEntity, RealmObject()