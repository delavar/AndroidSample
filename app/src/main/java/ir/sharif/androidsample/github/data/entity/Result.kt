package ir.sharif.androidsample.github.data.entity

import androidx.annotation.StringRes

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        @StringRes val messageResId: Int,
        val cause: Throwable? = null
    ) : Result<Nothing>()
}