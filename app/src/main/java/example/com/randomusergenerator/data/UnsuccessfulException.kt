package example.com.randomusergenerator.data

class UnsuccessfulException(responseCode: Int? = null) :
    Exception("Request was not successful. ${responseCode?.let { "Error code: $it" } ?: "Unknown error"}")