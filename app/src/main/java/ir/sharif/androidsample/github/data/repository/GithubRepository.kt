package ir.sharif.androidsample.github.data.repository

import ir.sharif.androidsample.github.data.entity.GithubRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ir.sharif.androidsample.github.data.entity.Result
import ir.sharif.androidsample.github.data.source.RepoNetworkDataSource

class GithubRepository(
    private val reposNetworkDataSource: RepoNetworkDataSource,
) {

    suspend fun getUserRepositories(username: String): Result<List<GithubRepo>> = withContext(
        Dispatchers.IO
    ) {
        reposNetworkDataSource.getUserRepositories(username = username)
    }
} 