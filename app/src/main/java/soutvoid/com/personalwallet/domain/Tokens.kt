package soutvoid.com.personalwallet.domain

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Tokens(var authorizationToken: String = "",
                  var refreshToken: String = "",
                  @Index var userId: Long = -1,
                  @PrimaryKey override var localId: Long = 0) : RealmObject(), Serializable, IBaseEntity