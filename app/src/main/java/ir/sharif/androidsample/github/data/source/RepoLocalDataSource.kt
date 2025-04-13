package ir.sharif.androidsample.github.data.source

import ir.sharif.androidsample.R
import ir.sharif.androidsample.github.data.database_room.dao.GithubRepoDao
import ir.sharif.androidsample.github.data.database_room.entity.GithubRepoEntity
import ir.sharif.androidsample.github.data.entity.Result

class RepoLocalDataSource(
    private val dao: GithubRepoDao,
) {
    suspend fun getByName(ownerUsername: String): Result<List<GithubRepoEntity>> {
        return try {
            Result.Success(dao.getByOwnerUsername(ownerUsername))
        } catch (e: Exception) {
            Result.Error(
                messageResId = R.string.general_error_fetching,
                cause = e,
            )
        }
    }

    suspend fun insertAll(entities: List<GithubRepoEntity>): Result<Unit> {
        return try {
            Result.Success(dao.insertAll(entities))
        } catch (e: Exception) {
            Result.Error(
                messageResId = R.string.github_user_fetch_error,
                cause = e,
            )
        }
    }
}
