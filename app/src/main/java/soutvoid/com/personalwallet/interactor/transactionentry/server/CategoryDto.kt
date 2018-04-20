package soutvoid.com.personalwallet.interactor.transactionentry.server

import soutvoid.com.personalwallet.interactor.IBaseDto
import java.io.Serializable

data class CategoryDto(
        var id: Long = 0,
        var name: String = ""
) : Serializable, IBaseDto