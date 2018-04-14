package soutvoid.com.personalwallet.interactor.transactionentry.server

import java.io.Serializable

data class CategoryResponse(
        var id: Long = 0,
        var name: String = ""
) : Serializable