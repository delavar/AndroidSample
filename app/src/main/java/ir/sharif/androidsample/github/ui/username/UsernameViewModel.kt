package ir.sharif.androidsample.github.ui.username

import androidx.lifecycle.ViewModel
import ir.sharif.androidsample.github.data.repository.UserRepository

class UsernameViewModel(
    private val repository: UserRepository
) : ViewModel() {
    fun getLastUsername(): String? {
        return repository.getLastUsername()
    }
} 