package ir.sharif.androidsample.github.navigation

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ir.sharif.androidsample.github.di.GithubModule
import ir.sharif.androidsample.github.ui.detail.UserDetailsScreen
import ir.sharif.androidsample.github.ui.username.UsernameScreen

object GithubDestinations {
    const val USERNAME_ROUTE = "github_username"
    const val USER_DETAILS_ROUTE = "github_user_details/{username}"

    fun userDetailsRoute(username: String) = "github_user_details/$username"
}

fun NavGraphBuilder.githubGraph(navController: NavHostController) {
    composable(GithubDestinations.USERNAME_ROUTE) {
        val context = LocalContext.current
        val viewModel = viewModel {
            GithubModule.provideUsernameViewModel(context)
        }
        UsernameScreen(
            viewModel = viewModel,
            onNavigateToDetails = { username ->
                navController.navigate(GithubDestinations.userDetailsRoute(username))
            }
        )
    }

    composable(
        route = GithubDestinations.USER_DETAILS_ROUTE
    ) { backStackEntry ->
        val username = backStackEntry.arguments?.getString("username") ?: return@composable
        val context = LocalContext.current
        val viewModel = viewModel { GithubModule.provideUserDetailsViewModel(context) }

        UserDetailsScreen(
            viewModel = viewModel,
            username = username,
            onNavigateBack = { navController.popBackStack() }
        )
    }
} 