package ir.sharif.androidsample.github.ui.username

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UsernameScreen(
    viewModel: UsernameViewModel,
    onNavigateToDetails: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    
    LaunchedEffect(Unit) {
        viewModel.getLastUsername()?.let { lastUsername ->
            username = lastUsername
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("GitHub Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                if (username.isNotBlank()) {
                    onNavigateToDetails(username)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank()
        ) {
            Text("View Profile")
        }
    }
} 