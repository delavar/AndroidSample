package ir.sharif.androidsample.github.ui.detail

import androidx.annotation.StringRes
import ir.sharif.androidsample.github.domain.entity.UserDetailEntity

sealed class UserDetailState {
    object Loading : UserDetailState()
    data class Success(val data: UserDetailEntity) : UserDetailState()
    data class Error(@StringRes val messageResId: Int) : UserDetailState()
}