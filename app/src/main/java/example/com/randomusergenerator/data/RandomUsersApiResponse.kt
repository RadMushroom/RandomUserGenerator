package example.com.randomusergenerator.data

import example.com.randomusergenerator.domain.API
import example.com.randomusergenerator.domain.RandomUsersRepository

class RandomUsersApiResponse(private val randomAPI: API): RandomUsersRepository {

    override fun query(page: Int, results: Int): RandomAPIResponse {
        val response = DataUtils.executeOrThrowGenericError {
            randomAPI.getRandomUsers(page, results).execute()
        }
        return response.body() ?: throw RandomUsersRepository.ServerErrorException()
    }
}