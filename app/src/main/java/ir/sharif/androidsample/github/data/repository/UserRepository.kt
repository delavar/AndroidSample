package ir.sharif.androidsample.github.data.repository

import ir.sharif.androidsample.github.data.entity.GithubUser
import ir.sharif.androidsample.github.data.source.LastUserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ir.sharif.androidsample.github.data.entity.Result
import ir.sharif.androidsample.github.data.source.UserNetworkDataSource

class UserRepository(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val lastUserDataSource: LastUserDataSource
) {

    suspend fun getUser(username: String): Result<GithubUser> = withContext(Dispatchers.IO) {
        lastUserDataSource.saveLastUsername(username = username)
        userNetworkDataSource.getUser(username)
    }

    fun getLastUsername(): String? {
        return lastUserDataSource.getLastUsername()
    }
} 