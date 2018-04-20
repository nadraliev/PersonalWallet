package soutvoid.com.personalwallet.interactor.authorization.local

import io.reactivex.Observable
import soutvoid.com.personalwallet.domain.Tokens
import soutvoid.com.personalwallet.interactor.BaseRepository
import soutvoid.com.personalwallet.interactor.util.observableFromCallableOrError
import soutvoid.com.personalwallet.interactor.util.realmTransaction

class AuthorizationRepository : BaseRepository<Tokens>(Tokens::class.java), IAuthorizationRepository {

    override fun deleteAll(): Observable<Any> = observableFromCallableOrError {
        realmTransaction {
            where(Tokens::class.java).findAll().deleteAllFromRealm()
            Any()
        }
    }
}