package example.com.randomusergenerator.data

import com.google.gson.annotations.SerializedName

data class RandomAPIResponse(
    val results: List<Response>)

data class Response(
    val name: Name,
    val location: Location,
    val email: String,
    @SerializedName("dob")
    val dateOfBirthday: DOB,
    val phone: String
)

data class Name(
    val first: String,
    val last: String
)

data class Location(
    val city: String,
    val country: String
)

data class DOB(
    val date: String,
    val age: Int
)



