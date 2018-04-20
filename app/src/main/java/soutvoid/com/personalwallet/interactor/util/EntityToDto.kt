package soutvoid.com.personalwallet.interactor.util

import soutvoid.com.personalwallet.domain.user.User
import soutvoid.com.personalwallet.interactor.authorization.server.UserDto

fun User.toDto() =
        UserDto(id, email, password)