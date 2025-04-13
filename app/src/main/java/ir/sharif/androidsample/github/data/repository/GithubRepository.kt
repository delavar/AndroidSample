package ir.sharif.androidsample.github.data.repository

import ir.sharif.androidsample.github.data.database_room.entity.GithubRepoEntity
import ir.sharif.androidsample.github.data.database_room.entity.mapFromEntity
import ir.sharif.androidsample.github.data.database_room.entity.mapToEntity
import ir.sharif.androidsample.github.data.entity.GithubRepo
import ir.sharif.androidsample.github.data.entity.Result
import ir.sharif.androidsample.github.data.source.RepoLocalDataSource
import ir.sharif.androidsample.github.data.source.RepoNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepository(
    private val reposNetworkDataSource: RepoNetworkDataSource,
    private val reposLocalDataSource: RepoLocalDataSource,
) {

    suspend fun getUserRepositories(username: String): Result<List<GithubRepo>> = withContext(
        Dispatchers.IO
    ) {
        // first check if repos are available in local storage
        // we can implement a caching strategy here to discard the persisted data if they are out-dated and fetch new ones
        val localResult = reposLocalDataSource.getByName(ownerUsername = username)
        if (localResult is Result.Error) return@withContext localResult
        if ((localResult as Result.Success).data.isNotEmpty()) {
            val repos = localResult.data.map { it.mapFromEntity() }
            return@withContext Result.Success(repos)
        }

        // otherwise fetch and store them in local storage
        val networkResult = reposNetworkDataSource.getUserRepositories(username = username)
        if (networkResult is Result.Success) {
            val entities = networkResult.data.map { it.mapToEntity(username) }
            reposLocalDataSource.insertAll(entities) // ignore failed insertion
        }
        return@withContext networkResult
    }
}
