package soutvoid.com.personalwallet.interactor.util

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject

fun <T> realm(action: Realm.() -> T): T =
        Realm.getDefaultInstance().use(action)

fun <T> realmTransaction(action: Realm.() -> T?): T? =
        realm {
            var result: T? = null
            executeTransaction {
                result = action()
            }
            return@realm result
        }

fun <T : RealmObject> T.toUnmanaged(): T =
        soutvoid.com.personalwallet.interactor.util.realm {
            copyFromRealm(this@toUnmanaged)
        }

fun <T : RealmModel> realmToList(action: Realm.() -> Iterable<T>): List<T> {
    return realm {
        return@realm action().let { copyFromRealm(it) }
    }
}
