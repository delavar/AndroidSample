package ir.sharif.androidsample.github.data.database_legacy.entity

data class GithubRepoEntity(
    val name: String,
    val description: String?,
    val stars: Int,
    val language: String?,
    val url: String,
    val ownerUsername: String,
)
