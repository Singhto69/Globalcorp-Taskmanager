package com.globalcorp.taskman.network

import androidx.lifecycle.AndroidViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.currentCoroutineContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import kotlin.coroutines.coroutineContext


private const val BASE_URL =
    "https://api.thecatapi.com/v1/images/"

private const val BASE_URL2 =
    "https://api.thecatapi.com/v1/images/search?limit=3&breeds=Sphinx&api_key="

private const val api_key = "9b686b6c-0496-4238-92c3-b787d3189983"
private const val limit = "2"
private const val breed = "Sphinx"

class CatApiService  {
    /* The call to create() function on a Retrofit object
     is expensive and the app needs only one instance of Retrofit API service.
     So, you expose the service to the rest of the app using object declaration.
     */

    object CatApi {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit =
            Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

        val retrofitService: CatApiService by lazy {
            retrofit.create(CatApiService::class.java)
        }

    }

    /*  defines how Retrofit talks to the web server using HTTP requests. */
    interface CatApiService {
        /* @GET annotation to tell Retrofit that this is GET request, and specify an endpoint,
        for that web service method. In this case the endpoint is called photos
         */
        @GET("search?limit=$limit&breeds=$breed&api_key=$api_key")
        //suspend fun getPhotos(): List<>
        suspend fun getCat(): List<CatObject>
    }

}