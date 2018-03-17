package soutvoid.com.personalwallet.interactor


/**
 * Created by andrew on 16.03.18.
 */
interface IBaseRepository<in ID, VALUE> {
    fun getAll(): Collection<VALUE>
    fun getById(id: ID): VALUE?
    fun create(value: VALUE)
    fun update(value: VALUE)
    fun delete(id: ID)
}