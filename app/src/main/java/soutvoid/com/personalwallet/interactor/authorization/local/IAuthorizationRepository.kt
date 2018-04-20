package soutvoid.com.personalwallet.interactor.authorization.local

import io.reactivex.Observable
import soutvoid.com.personalwallet.domain.Tokens
import soutvoid.com.personalwallet.interactor.IBaseRepository

interface IAuthorizationRepository : IBaseRepository<Long, Tokens> {
    fun deleteAll(): Observable<Any>
}