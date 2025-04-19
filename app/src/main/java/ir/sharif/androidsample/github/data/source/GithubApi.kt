package ir.sharif.androidsample.github.data.source

import ir.sharif.androidsample.github.data.entity.GithubRepo
import ir.sharif.androidsample.github.data.entity.GithubUser
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): GithubUser

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") username: String): List<GithubRepo>
} 