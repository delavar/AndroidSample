package ir.sharif.androidsample.github.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.sharif.androidsample.github.data.entity.GithubRepo
import ir.sharif.androidsample.github.data.entity.GithubUser
import ir.sharif.androidsample.github.domain.entity.UserDetailEntity

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailViewModel,
    username: String,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(username) {
        viewModel.loadUserDetails(username)
    }

    when (val currentState = state) {
        is UserDetailState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UserDetailState.Success -> {
            UserDetailContent(
                detailEntity = currentState.data,
                onNavigateBack = onNavigateBack
            )
        }

        is UserDetailState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = currentState.messageResId),
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateBack) {
                    Text("Go Back")
                }
            }
        }
    }
}

@Composable
private fun UserDetailContent(
    detailEntity: UserDetailEntity,
    onNavigateBack: () -> Unit
) {
    Scaffold(topBar = {
        TopBar(
            title = detailEntity.user.name ?: detailEntity.user.username,
            onNavigateBack = onNavigateBack
        )
    }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                UserInfoSection(detailEntity.user)
            }
            if (!detailEntity.repos.isNullOrEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Public Repositories (${detailEntity.repos.size})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(detailEntity.repos) { repo ->
                    RepositoryItem(repo)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}

@Composable
fun TopBar(title: String, onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = onNavigateBack) {
            Text("Back")
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun UserInfoSection(user: GithubUser) {
    Column {
        InfoRow("Username", user.username)
        InfoRow("Email", user.email ?: "Not available")
        InfoRow("Created at", user.createdAt)
        InfoRow("Followers", user.followers.toString())
        InfoRow("Following", user.following.toString())
        if (user.bio != null) {
            InfoRow("Bio", user.bio)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}

@Composable
private fun RepositoryItem(repo: GithubRepo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            if (repo.description != null) {
                Text(
                    text = repo.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("⭐ ${repo.stars}")
                repo.language?.let { Text("Language: $it") }
            }
        }
    }
} 