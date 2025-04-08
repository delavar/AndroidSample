package ir.sharif.androidsample.github.domain.usecase

import ir.sharif.androidsample.R
import ir.sharif.androidsample.github.data.repository.GithubRepository
import ir.sharif.androidsample.github.domain.entity.UserDetailEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ir.sharif.androidsample.github.data.entity.Result

class UserDetailUseCase(private val repository: GithubRepository) {
    suspend operator fun invoke(username: String): Result<UserDetailEntity> = withContext(Dispatchers.IO) {
        val user = repository.getUser(username)
        val repos = repository.getUserRepositories(username)
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