package soutvoid.com.personalwallet.interactor.transactionentry.local

import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.IBaseRepository

/**
 * Created by andrew on 16.03.18.
 */
interface ICategoryRepository : IBaseRepository<Long, Category>