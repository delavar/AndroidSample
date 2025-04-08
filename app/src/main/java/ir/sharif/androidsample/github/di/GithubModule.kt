package ir.sharif.androidsample.github.di

import android.content.Context
import android.content.SharedPreferences
import ir.sharif.androidsample.github.data.source.GithubApi
import ir.sharif.androidsample.github.data.repository.GithubRepository
import ir.sharif.androidsample.github.data.source.GithubDataSource
import ir.sharif.androidsample.github.data.source.LastUserDataSource
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

    private fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    private fun provideGithubDataSource(retrofit: Retrofit): GithubDataSource {
        return GithubDataSource(provideGithubApi(retrofit = retrofit))
    }

    fun provideGithubRepository(context: Context): GithubRepository {
        val okHttpClient = provideOkHttpClient()
        val retrofit = provideRetrofit(okHttpClient = okHttpClient)
        val githubDataSource = provideGithubDataSource(retrofit = retrofit)
        val lastUserDataSource = provideLastUserDataSource(context = context)
        return GithubRepository(
            githubDataSource = githubDataSource,
            lastUserDataSource = lastUserDataSource
        )
    }

    fun provideUserDetailUseCase(context: Context): UserDetailUseCase {
        return UserDetailUseCase(repository = provideGithubRepository(context = context))
    }

    fun provideUsernameViewModel(context: Context): UsernameViewModel {
        return UsernameViewModel(provideGithubRepository(context = context))
    }

    fun provideUserDetailsViewModel(context: Context): UserDetailViewModel {
        return UserDetailViewModel(userDetailUseCase = provideUserDetailUseCase(context))
    }
} 