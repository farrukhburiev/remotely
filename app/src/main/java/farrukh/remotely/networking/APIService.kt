package farrukh.remotely.networking


import android.icu.util.ULocale.Category
import farrukh.remotely.model.CartData
import farrukh.remotely.model.Login
import farrukh.remotely.model.Product
import farrukh.remotely.model.ProductData
import farrukh.remotely.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @POST("/auth/login")
    fun login(@Body login: Login): Call<User>

    @GET("/products")
    fun getAllProducts():Call<ProductData>

    @GET("/products/categories")
    fun getAllCategories():Call<List<String>>

    @GET("/products/{id}")
    fun getProduct(@Path("id") id: Int): Call<Product>

    @GET("/users/{id}/carts")
    fun getCartsOfUser(@Path("id") id: Int):Call<CartData>

    @GET("/products/category/{category}")
    fun getProductsofCategory(@Path("category") category: String):Call<ProductData>

    @GET("/products/search")
    fun searchByName(@Query("q") name: String): Call<ProductData>


}