package ir.sharif.androidsample.github.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repo")
data class GithubRepoEntity(
    @PrimaryKey @ColumnInfo(name = "repo_url") val url: String,
    @ColumnInfo("owner_username") val ownerUsername: String,
    @ColumnInfo(name = "repo_name") val name: String,
    val description: String?,
    @ColumnInfo(name = "star_count") val stars: Int,
    val language: String?,
)
