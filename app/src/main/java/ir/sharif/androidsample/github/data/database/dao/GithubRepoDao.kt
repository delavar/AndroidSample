package ir.sharif.androidsample.github.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ir.sharif.androidsample.github.data.database.entity.GithubRepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<GithubRepoEntity>)

    @Query("SELECT * FROM github_repo")
    suspend fun getAll(): List<GithubRepoEntity>

    @Query("SELECT * FROM github_repo")
    fun getAllFlow(): Flow<List<GithubRepoEntity>>

    @Query("SELECT * FROM github_repo WHERE owner_username = :ownerUsername")
    suspend fun getByName(ownerUsername: String): GithubRepoEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(repo: GithubRepoEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateAll(repos: List<GithubRepoEntity>)

    @Delete
    suspend fun delete(repo: GithubRepoEntity)

    @Query("DELETE FROM github_repo")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(repos: List<GithubRepoEntity>) {
        deleteAll()
        insertAll(repos)
    }
}
