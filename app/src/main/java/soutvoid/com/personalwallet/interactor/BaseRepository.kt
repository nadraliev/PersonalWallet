package soutvoid.com.personalwallet.interactor

import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import soutvoid.com.personalwallet.domain.IBaseEntity
import soutvoid.com.personalwallet.domain.util.ID_KEY
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by andrew on 16.03.18.
 */
open class BaseRepository<VALUE : IBaseEntity>(internal val dbHelper: Realm,
                                               internal val clazz: Class<VALUE>)
    : IBaseRepository<Long, VALUE> {

    override fun getAll(): Collection<VALUE> =
            dbHelper.where(clazz).findAll()

    override fun getById(id: Long): VALUE? =
            dbHelper.where(clazz).equalTo(ID_KEY, id).findFirst()

    override fun create(value: VALUE) {
        dbHelper.executeTransactionAsync {
            val lastId = AtomicLong(it.where(clazz).max(ID_KEY) as Long? ?: -1)
            it.copyToRealm(value.apply { id = lastId.incrementAndGet() })
        }
    }

    override fun update(value: VALUE) {
        dbHelper.executeTransactionAsync {
            it.copyToRealmOrUpdate(value)
        }
    }

    override fun delete(id: Long) {
        getById(id)?.deleteFromRealm()
    }
}