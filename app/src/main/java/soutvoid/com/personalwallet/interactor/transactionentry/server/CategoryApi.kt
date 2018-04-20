package soutvoid.com.personalwallet.interactor.transactionentry.server

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import soutvoid.com.personalwallet.domain.transactionentry.Category

interface CategoryApi {

    companion object {
        private const val CATEGORIES_URL = "/categories"
    }

    @POST("$CATEGORIES_URL/add")
    fun addCategory(@Body category: Category): Observable<CategoryDto>

}