package example.com.randomusergenerator.data

import com.google.gson.Gson
import example.com.randomusergenerator.domain.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIProvider {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val RandomAPI: API = Retrofit.Builder()
        .baseUrl(API.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(okHttpClient)
        .build()
        .create(API::class.java)

    fun provideRandomApi(): API = RandomAPI
}