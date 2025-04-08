package ir.sharif.androidsample.github.domain.entity

import ir.sharif.androidsample.github.data.entity.GithubRepo
import ir.sharif.androidsample.github.data.entity.GithubUser

data class UserDetailEntity(
    val user: GithubUser,
    val repos: List<GithubRepo>?
)