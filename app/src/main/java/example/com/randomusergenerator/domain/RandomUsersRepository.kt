package example.com.randomusergenerator.domain

import example.com.randomusergenerator.data.RandomAPIResponse
import example.com.randomusergenerator.data.UnsuccessfulException

interface RandomUsersRepository {
    @Throws(UnsuccessfulException::class)
    fun query(page: Int, results: Int): RandomAPIResponse

    class ServerErrorException : Exception("Server error")
}