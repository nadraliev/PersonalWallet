package soutvoid.com.personalwallet.interactor.authorization.local

import soutvoid.com.personalwallet.domain.Tokens
import soutvoid.com.personalwallet.interactor.IBaseRepository

interface IAuthorizationRepository : IBaseRepository<Long, Tokens>