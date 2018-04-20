package soutvoid.com.personalwallet.interactor.util

import soutvoid.com.personalwallet.domain.Tokens
import soutvoid.com.personalwallet.domain.user.User
import soutvoid.com.personalwallet.interactor.authorization.server.TokensDto
import soutvoid.com.personalwallet.interactor.authorization.server.UserDto

fun TokensDto.toEntity() =
        Tokens(authorizationToken, refreshToken, userId)

fun UserDto.toEntity() =
        User(id, email, password)