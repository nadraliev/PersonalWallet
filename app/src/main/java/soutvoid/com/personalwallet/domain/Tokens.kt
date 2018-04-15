package soutvoid.com.personalwallet.domain

import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Tokens(var authorizationToken: String = "",
                  var refreshToken: String = "",
                  @PrimaryKey override var localId: Long = 0) : Serializable, IBaseEntity