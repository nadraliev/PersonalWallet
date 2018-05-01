package soutvoid.com.personalwallet.interactor.transactionentry.local

import io.reactivex.Observable
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.BaseRepository
import soutvoid.com.personalwallet.interactor.util.observableFromCallableOrEmpty
import soutvoid.com.personalwallet.interactor.util.realmTransaction

/**
 * Created by andrew on 16.03.18.
 */
class CategoryRepository
    : BaseRepository<Category>(Category::class.java), ICategoryRepository {

    override fun deleteAll(): Observable<Category> = observableFromCallableOrEmpty {
        realmTransaction {
            where(Category::class.java).findAll().deleteAllFromRealm()
            return@realmTransaction null
        }
    }
}