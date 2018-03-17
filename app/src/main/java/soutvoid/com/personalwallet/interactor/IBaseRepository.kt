package soutvoid.com.personalwallet.interactor

import io.reactivex.Flowable


/**
 * Created by andrew on 16.03.18.
 */
interface IBaseRepository<in ID, VALUE, COLLECTION : Collection<VALUE>> {
    fun getAll(): Flowable<COLLECTION>
    fun getById(id: ID): Flowable<VALUE?>?
    fun create(value: VALUE)
    fun update(value: VALUE)
    fun delete(id: ID)
}