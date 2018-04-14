package soutvoid.com.personalwallet.interactor

import io.reactivex.Observable
import io.realm.RealmObject
import soutvoid.com.personalwallet.domain.util.ID_KEY
import soutvoid.com.personalwallet.domain.util.trySetId
import soutvoid.com.personalwallet.interactor.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by andrew on 16.03.18.
 */
open class BaseRepository<VALUE : RealmObject>(internal val clazz: Class<VALUE>)
    : IBaseRepository<Long, VALUE> {

    override fun getAll(): Observable<Iterable<VALUE>> = observableFromCallableOrError {
        realmToList {
            where(clazz).findAll()
        }
    }

    override fun getById(id: Long): Observable<VALUE> = observableFromCallableOrError {
        realm {
            where(clazz).equalTo(ID_KEY, id).findFirst()?.toUnmanaged()
        }
    }

    override fun create(value: VALUE): Observable<VALUE> = observableFromCallableOrError {
        realmTransaction {
            val lastId = AtomicLong(where(clazz).max(ID_KEY) as Long? ?: -1)
            return@realmTransaction copyToRealmOrUpdate(value.apply { trySetId(lastId.incrementAndGet()) })
                    .toUnmanaged()
        }
    }

    override fun update(value: VALUE): Observable<VALUE> = observableFromCallableOrError {
        realmTransaction {
            copyToRealmOrUpdate(value).toUnmanaged()
        }
    }

    override fun delete(id: Long): Observable<VALUE> = observableFromCallableOrEmpty {
        realmTransaction {
            where(clazz).equalTo(ID_KEY, id).findFirst()?.deleteFromRealm()
            return@realmTransaction null
        }
    }
}