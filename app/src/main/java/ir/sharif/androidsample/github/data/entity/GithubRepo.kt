package ir.sharif.androidsample.github.data.entity

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("language") val language: String?,
    @SerializedName("html_url") val url: String
)