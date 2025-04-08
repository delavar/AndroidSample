package ir.sharif.androidsample.github.di

import android.content.Context
import android.content.SharedPreferences
import ir.sharif.androidsample.github.data.repository.GithubRepository
import ir.sharif.androidsample.github.data.source.GithubApi
import ir.sharif.androidsample.github.data.repository.UserRepository
import ir.sharif.androidsample.github.data.source.UserNetworkDataSource
import ir.sharif.androidsample.github.data.source.LastUserDataSource
import ir.sharif.androidsample.github.data.source.RepoNetworkDataSource
import ir.sharif.androidsample.github.domain.usecase.UserDetailUseCase
import ir.sharif.androidsample.github.ui.detail.UserDetailViewModel
import ir.sharif.androidsample.github.ui.username.UsernameViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubModule {
    private const val BASE_URL = "https://api.github.com/"
    private const val PREFS_NAME = "github_prefs"

    private val okHttpClient by lazy { provideOkHttpClient() }
    private val retrofit by lazy { provideRetrofit(okHttpClient = okHttpClient) }

    private fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun provideLastUserDataSource(context: Context): LastUserDataSource {
        return LastUserDataSource(sharedPreferences = provideSharedPreferences(context = context))
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideGithubApi(): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    private fun provideUserNetworkDataSource(): UserNetworkDataSource {
        return UserNetworkDataSource(provideGithubApi())
    }

    private fun provideReposNetworkDataSource(): RepoNetworkDataSource {
        return RepoNetworkDataSource(provideGithubApi())
    }

    fun provideGithubRepository(): GithubRepository {
        val reposNetworkDataSource = provideReposNetworkDataSource()
        return GithubRepository(
            reposNetworkDataSource = reposNetworkDataSource,
        )
    }

    fun provideUserRepository(context: Context): UserRepository {
        val userNetworkDataSource = provideUserNetworkDataSource()
        val lastUserDataSource = provideLastUserDataSource(context = context)
        return UserRepository(
            userNetworkDataSource = userNetworkDataSource,
            lastUserDataSource = lastUserDataSource
        )
    }

    fun provideUserDetailUseCase(context: Context): UserDetailUseCase {
        return UserDetailUseCase(
            githubRepository = provideGithubRepository(),
            userRepository = provideUserRepository(context)
        )
    }

    fun provideUsernameViewModel(context: Context): UsernameViewModel {
        return UsernameViewModel(provideUserRepository(context = context))
    }

    fun provideUserDetailsViewModel(context: Context): UserDetailViewModel {
        return UserDetailViewModel(userDetailUseCase = provideUserDetailUseCase(context))
    }
} 