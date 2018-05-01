package soutvoid.com.personalwallet.interactor.util

import soutvoid.com.personalwallet.domain.Tokens
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.domain.user.User
import soutvoid.com.personalwallet.interactor.authorization.server.TokensDto
import soutvoid.com.personalwallet.interactor.authorization.server.UserDto
import soutvoid.com.personalwallet.interactor.transactionentry.server.CategoryDto

fun TokensDto.toEntity() =
        Tokens(authorizationToken, refreshToken, userId)

fun UserDto.toEntity() =
        User(id, email, password)

fun CategoryDto.toEntity() =
        Category(name, 0, id)