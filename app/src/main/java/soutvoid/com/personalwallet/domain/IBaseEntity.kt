package soutvoid.com.personalwallet.domain

import io.realm.RealmModel

/**
 * Created by andrew on 17.03.18.
 */
interface IBaseEntity : RealmModel {
    var id: Long
}