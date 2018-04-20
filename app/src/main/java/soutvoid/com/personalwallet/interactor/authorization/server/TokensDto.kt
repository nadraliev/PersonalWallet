package soutvoid.com.personalwallet.interactor.authorization.server

import soutvoid.com.personalwallet.interactor.IBaseDto

data class TokensDto(
        var userId: Long,
        var authorizationToken: String,
        var refreshToken: String
) : IBaseDto