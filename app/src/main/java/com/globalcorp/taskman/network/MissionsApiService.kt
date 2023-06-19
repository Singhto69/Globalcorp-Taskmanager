package com.globalcorp.taskman.network

import com.globalcorp.taskman.models.Mission
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "http://92.32.46.83:80/"

private const val api_key = ""

private const val username = "Joe%20Average"

class MissionsApiService {
    /* The call to create() function on a Retrofit object
     is expensive and the app needs only one instance of Retrofit API service.
     So, you expose the service to the rest of the app using object declaration.
     */

    object MissionsApi {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit =
            Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

        val retrofitService: MissionsApiService by lazy {
            retrofit.create(MissionsApiService::class.java)
        }

    }

    /*  defines how Retrofit talks to the web server using HTTP requests. */
    interface MissionsApiService {
        /* @GET annotation to tell Retrofit that this is GET request, and specify an endpoint,
        for that web service method. In this case the endpoint is called photos
         */
        @GET("missions?username=$username")
        //suspend fun getPhotos(): List<>
        suspend fun getMissions(): List<Mission>
    }

}