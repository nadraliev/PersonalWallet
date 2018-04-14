package soutvoid.com.personalwallet.interactor

import io.reactivex.Observable


/**
 * Created by andrew on 16.03.18.
 */
interface IBaseRepository<in ID, VALUE> {
    fun getAll(): Observable<Iterable<VALUE>>
    fun getById(id: ID): Observable<VALUE>
    fun create(value: VALUE): Observable<VALUE>
    fun update(value: VALUE): Observable<VALUE>
    fun delete(id: ID): Observable<VALUE>
}