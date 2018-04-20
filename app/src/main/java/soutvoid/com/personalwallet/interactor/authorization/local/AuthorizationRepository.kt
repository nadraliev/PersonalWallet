package soutvoid.com.personalwallet.interactor.authorization.local

import soutvoid.com.personalwallet.domain.Tokens
import soutvoid.com.personalwallet.interactor.BaseRepository

class AuthorizationRepository : BaseRepository<Tokens>(Tokens::class.java), IAuthorizationRepository