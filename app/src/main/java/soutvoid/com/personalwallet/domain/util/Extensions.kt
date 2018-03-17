package soutvoid.com.personalwallet.domain.util

import io.realm.RealmObject
import soutvoid.com.personalwallet.domain.IBaseEntity

/**
 * Created by andrew on 17.03.18.
 */
fun RealmObject.trySetId(id: Long) {
    if (this is IBaseEntity) {
        this.id = id
    }
}