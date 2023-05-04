package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

class MarsApiService {
    /* The call to create() function on a Retrofit object
     is expensive and the app needs only one instance of Retrofit API service.
     So, you expose the service to the rest of the app using object declaration.
     */

    object MarsApi {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit =
            Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()

        val retrofitService: MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)
        }

    }

    /*  defines how Retrofit talks to the web server using HTTP requests. */
    interface MarsApiService {
        /* @GET annotation to tell Retrofit that this is GET request, and specify an endpoint,
        for that web service method. In this case the endpoint is called photos
         */
        @GET("photos")
        suspend fun getPhotos(): List<MarsPhoto>
    }

}
