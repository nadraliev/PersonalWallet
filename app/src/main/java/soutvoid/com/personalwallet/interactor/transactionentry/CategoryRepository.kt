package soutvoid.com.personalwallet.interactor.transactionentry

import io.realm.Realm
import soutvoid.com.personalwallet.interactor.BaseRepository

/**
 * Created by andrew on 16.03.18.
 */
class CategoryRepository(dbHelper: Realm) : BaseRepository(dbHelper), ICategoryRepository {

}