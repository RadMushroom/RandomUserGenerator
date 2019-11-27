package example.com.randomusergenerator.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RandomUser(
    val gender: String,
    val firstName: String,
    val secondName: String,
    val email: String,
    val phone: String,
    val city: String,
    val country: String,
    val dateOfBirthday: String,
    val age: Int,
    val thumbnail: String,
    val image: String
) : Parcelable