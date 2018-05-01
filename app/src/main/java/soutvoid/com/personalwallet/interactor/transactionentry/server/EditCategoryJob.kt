package soutvoid.com.personalwallet.interactor.transactionentry.server

import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.BaseServerJob
import soutvoid.com.personalwallet.interactor.transactionentry.local.ICategoryRepository

class EditCategoryJob(val category: Category) : BaseServerJob(0) {

    private val categoryApi: CategoryApi
        get() = kodein.instance()
    private val categoryRepository: ICategoryRepository
        get() = kodein.instance()

    override fun onRun() {
        categoryApi.editCategory(category).blockingSingle(Any())
        categoryRepository.update(category).blockingSubscribe()
    }

    override fun onAdded() {
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }
}