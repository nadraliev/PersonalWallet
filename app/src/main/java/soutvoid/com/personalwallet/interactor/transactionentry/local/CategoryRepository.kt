package soutvoid.com.personalwallet.interactor.transactionentry.local

import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.BaseRepository

/**
 * Created by andrew on 16.03.18.
 */
class CategoryRepository
    : BaseRepository<Category>(Category::class.java), ICategoryRepository