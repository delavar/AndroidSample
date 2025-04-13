package ir.sharif.androidsample.github.data.database_room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.sharif.androidsample.github.data.entity.GithubRepo

@Entity(tableName = "github_repo")
data class GithubRepoEntity(
    @PrimaryKey @ColumnInfo(name = "repo_url") val url: String,
    @ColumnInfo("owner_username") val ownerUsername: String,
    @ColumnInfo(name = "repo_name") val name: String,
    val description: String?,
    @ColumnInfo(name = "star_count") val stars: Int,
    val language: String?,
)

internal fun GithubRepo.mapToEntity(username: String): GithubRepoEntity {
    return GithubRepoEntity(
        url = url,
        name = name,
        description = description,
        stars = stars,
        language = language,
        ownerUsername = username
    )
}

internal fun GithubRepoEntity.mapFromEntity(): GithubRepo {
    return GithubRepo(
        name = name,
        description = description,
        stars = stars,
        language = language,
        url = url
    )
}
