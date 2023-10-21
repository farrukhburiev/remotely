package farrukh.remotely.networking


import android.icu.util.ULocale.Category
import farrukh.remotely.model.Login
import farrukh.remotely.model.Product
import farrukh.remotely.model.ProductData
import farrukh.remotely.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @POST("/auth/login")
    fun login(@Body login: Login): Call<User>

    @GET("/products")
    fun getAllProducts():Call<ProductData>

    @GET("/products/categories")
    fun getAllCategories():Call<List<String>>

    @GET("/products/{id}")
    fun getProduct(@Path("id") id: Int): Call<Product>

    @GET("/products/category/{category}")
    fun getProductsofCategory(@Path("category") category: String):Call<ProductData>


}