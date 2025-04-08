package ir.sharif.androidsample.github.data.entity

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login") val username: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("public_repos") val publicRepos: Int,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int,
    @SerializedName("avatar_url") val avatarUrl: String
)