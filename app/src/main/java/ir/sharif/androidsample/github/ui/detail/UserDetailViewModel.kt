package ir.sharif.androidsample.github.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.sharif.androidsample.github.domain.usecase.UserDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ir.sharif.androidsample.github.data.entity.Result

class UserDetailViewModel(
    private val userDetailUseCase: UserDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val state: StateFlow<UserDetailState> = _state

    fun loadUserDetails(username: String) {
        viewModelScope.launch {
            _state.value = UserDetailState.Loading
            when (val result = userDetailUseCase(username = username)) {
                is Result.Success -> {
                    _state.value = UserDetailState.Success(result.data)
                }

                is Result.Error -> {
                    _state.value = UserDetailState.Error(result.messageResId)
                }
            }
        }
    }
}