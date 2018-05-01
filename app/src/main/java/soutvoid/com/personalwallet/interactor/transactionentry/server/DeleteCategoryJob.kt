package soutvoid.com.personalwallet.interactor.transactionentry.server

import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.BaseServerJob
import soutvoid.com.personalwallet.interactor.transactionentry.local.ICategoryRepository

class DeleteCategoryJob(val categoryLocalId: Long) : BaseServerJob(0) {

    private val categoryApi: CategoryApi
        get() = kodein.instance()
    private val categoryRepository: ICategoryRepository
        get() = kodein.instance()

    override fun onRun() {
        val category = categoryRepository.getById(categoryLocalId).blockingFirst(Category())
        if (category.id != 0L) {
            categoryApi.deleteCategory(category.id).blockingSingle(Any())
            categoryRepository.delete(category.id).blockingSubscribe()
        }
    }

    override fun onAdded() {
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }
}