package soutvoid.com.personalwallet.interactor.authorization.server

import soutvoid.com.personalwallet.interactor.IBaseDto

data class UserDto(
        var id: Long? = null,
        var email: String,
        var password: String
) : IBaseDto