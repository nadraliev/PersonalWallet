package soutvoid.com.personalwallet.interactor.transactionentry.server

import io.reactivex.Observable
import retrofit2.http.*
import soutvoid.com.personalwallet.domain.transactionentry.Category

interface CategoryApi {

    companion object {
        private const val CATEGORIES_URL = "/categories"
    }

    @POST("$CATEGORIES_URL/add")
    fun addCategory(@Body category: Category): Observable<CategoryDto>

    @DELETE("$CATEGORIES_URL/delete")
    fun deleteCategory(@Query("id") id: Long): Observable<Any>

    @POST("$CATEGORIES_URL/edit")
    fun editCategory(@Body category: Category): Observable<Any>

    @GET(CATEGORIES_URL)
    fun getById(@Query("id") id: Long): Observable<CategoryDto>

    @GET(CATEGORIES_URL)
    fun getAll(): Observable<List<CategoryDto>>
}