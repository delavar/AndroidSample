package ir.sharif.androidsample.github.data.repository

import ir.sharif.androidsample.R
import ir.sharif.androidsample.github.data.source.GithubApi
import ir.sharif.androidsample.github.data.entity.GithubRepo
import ir.sharif.androidsample.github.data.entity.GithubUser
import ir.sharif.androidsample.github.data.source.LastUserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ir.sharif.androidsample.github.data.entity.Result
import ir.sharif.androidsample.github.data.source.GithubDataSource

class GithubRepository(
    private val githubDataSource: GithubDataSource,
    private val lastUserDataSource: LastUserDataSource
) {

    suspend fun getUser(username: String): Result<GithubUser> = withContext(Dispatchers.IO) {
        lastUserDataSource.saveLastUsername(username = username)
        githubDataSource.getUser(username)
    }

    suspend fun getUserRepositories(username: String): Result<List<GithubRepo>> = withContext(Dispatchers.IO) {
        githubDataSource.getUserRepositories(username)
    }

    fun getLastUsername(): String? {
        return lastUserDataSource.getLastUsername()
    }
} 