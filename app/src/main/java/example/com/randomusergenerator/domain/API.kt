package example.com.randomusergenerator.domain

import example.com.randomusergenerator.data.RandomAPIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    companion object{
        const val BASE_URL = "https://randomuser.me/api/"
    }

    @GET(".")
    fun getRandomUsers(
        @Query("page") page: Int ,
        @Query("results") results: Int,
        @Query("inc") inc: String = "gender,name,location,email,dob,phone,picture"
    ): Call<RandomAPIResponse>
}