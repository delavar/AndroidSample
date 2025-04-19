package ir.sharif.androidsample.github.data.source

import ir.sharif.androidsample.R
import ir.sharif.androidsample.github.data.entity.GithubRepo
import ir.sharif.androidsample.github.data.entity.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoNetworkDataSource(private val api: GithubApi) {
    suspend fun getUserRepositories(username: String): Result<List<GithubRepo>> = withContext(
        Dispatchers.IO
    ) {
        try {
            Result.Success(data = api.getUserRepositories(username))
        } catch (e: Exception) {
            Result.Error(cause = e, messageResId = R.string.general_error_fetching)
        }
    }
}