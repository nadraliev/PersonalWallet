package soutvoid.com.personalwallet.interactor

import io.realm.Realm

/**
 * Created by andrew on 16.03.18.
 */
open class BaseRepository(internal val dbHelper: Realm) : IBaseRepository