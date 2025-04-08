package ir.sharif.androidsample.github.ui.username

import androidx.lifecycle.ViewModel
import ir.sharif.androidsample.github.data.repository.GithubRepository

class UsernameViewModel(
    private val repository: GithubRepository
) : ViewModel() {
    fun getLastUsername(): String? {
        return repository.getLastUsername()
    }
} 