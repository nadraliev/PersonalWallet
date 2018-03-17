package soutvoid.com.personalwallet.interactor

import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import soutvoid.com.personalwallet.domain.util.ID_KEY
import soutvoid.com.personalwallet.domain.util.trySetId
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by andrew on 16.03.18.
 */
open class BaseRepository<VALUE : RealmObject>(internal val dbHelper: Realm,
                                               internal val clazz: Class<VALUE>)
    : IBaseRepository<Long, VALUE, RealmResults<VALUE>> {

    override fun getAll(): Flowable<RealmResults<VALUE>> =
            dbHelper.where(clazz).findAll().asFlowable()

    override fun getById(id: Long): Flowable<VALUE?>? =
            dbHelper.where(clazz).equalTo(ID_KEY, id).findFirst()?.asFlowable()

    override fun create(value: VALUE) {
        dbHelper.executeTransactionAsync {
            val lastId = AtomicLong(it.where(clazz).max(ID_KEY) as Long? ?: -1)
            it.copyToRealm(value.apply { trySetId(lastId.incrementAndGet()) })
        }
    }

    override fun update(value: VALUE) {
        dbHelper.executeTransactionAsync {
            it.copyToRealmOrUpdate(value)
        }
    }

    override fun delete(id: Long) {
        dbHelper.where(clazz).equalTo(ID_KEY, id).findFirst()?.deleteFromRealm()
    }
}