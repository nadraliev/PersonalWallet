package soutvoid.com.personalwallet.interactor.transactionentry.server

import com.github.salomonbrys.kodein.instance
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.interactor.BaseServerJob
import soutvoid.com.personalwallet.interactor.transactionentry.local.ICategoryRepository

class AddCategoryJob(category: Category) : BaseServerJob(0) {

    private val categoryApi: CategoryApi
        get() = kodein.instance()
    private val categoryRepository: ICategoryRepository
        get() = kodein.instance()

    override fun onRun() {

    }

    override fun onAdded() {
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }
}