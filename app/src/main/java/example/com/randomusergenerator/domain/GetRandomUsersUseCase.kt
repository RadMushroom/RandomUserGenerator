package example.com.randomusergenerator.domain

class GetRandomUsersUseCase(private val page: Int,
                            private val randomUsersRepository: RandomUsersRepository) {

    companion object{
        private const val RESULTS_PER_PAGE = 20
    }

    fun execute(): List<RandomUser> {
        val result = randomUsersRepository.query(page, RESULTS_PER_PAGE)
        return result.results.map {
            RandomUser(
                it.name.first, it.name.last, it.email, it.phone, it.location.city,
                it.location.country, it.dateOfBirthday.date, it.dateOfBirthday.age
            )
        }
    }
}
