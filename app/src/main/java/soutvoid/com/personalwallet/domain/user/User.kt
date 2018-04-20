package soutvoid.com.personalwallet.domain.user

data class User(
        var id: Long? = null,
        var email: String,
        var password: String
)