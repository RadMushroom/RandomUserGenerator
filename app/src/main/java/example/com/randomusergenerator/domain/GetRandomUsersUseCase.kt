package example.com.randomusergenerator.domain

class GetRandomUsersUseCase(
    private val randomUsersRepository: RandomUsersRepository
) {

    companion object {
        private const val RESULTS_PER_PAGE = 20
    }

    fun execute(params: Params): List<RandomUser> {
        val result = randomUsersRepository.query(params.page, params.resultsPerPage)
        return result.results.map {
            RandomUser(
                it.gender, it.name.first, it.name.last, it.email, it.phone, it.location.city,
                it.location.country, it.dateOfBirthday.date, it.dateOfBirthday.age,
                it.picture.thumbnail, it.picture.large
            )
        }
    }
    data class Params(val page: Int, val resultsPerPage: Int = RESULTS_PER_PAGE)
}
