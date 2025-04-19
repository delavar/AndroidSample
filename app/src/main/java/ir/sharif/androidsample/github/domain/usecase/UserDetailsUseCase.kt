package ir.sharif.androidsample.github.domain.usecase

import ir.sharif.androidsample.R
import ir.sharif.androidsample.github.data.entity.GithubRepo
import ir.sharif.androidsample.github.data.repository.UserRepository
import ir.sharif.androidsample.github.domain.entity.UserDetailEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ir.sharif.androidsample.github.data.entity.Result
import ir.sharif.androidsample.github.data.repository.GithubRepository

class UserDetailUseCase(
    private val userRepository: UserRepository,
    private val githubRepository: GithubRepository,
) {
    suspend operator fun invoke(username: String): Result<UserDetailEntity> = withContext(
        Dispatchers.IO
    ) {
        val user = userRepository.getUser(username)
        val repos = githubRepository.getUserRepositories(username)
        when (user) {
            is Result.Success -> {
                Result.Success(UserDetailEntity(user.data, (repos as? Result.Success)?.data))
            }

            is Result.Error -> {
                Result.Error(R.string.github_user_fetch_error, user.cause)
            }
        }
    }
}