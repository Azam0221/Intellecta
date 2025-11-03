package com.example.intellecta.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.intellecta.network.ApiService
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun TestApiScreen() {
    val apiService: ApiService = koinInject()
    val scope = rememberCoroutineScope()

    var healthResult by remember { mutableStateOf("Not tested") }
    var authResult by remember { mutableStateOf("Not tested") }
    var userResult by remember { mutableStateOf("Not tested") }
    var integrationResult by remember { mutableStateOf("Not tested") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Backend Connection Tests", style = MaterialTheme.typography.headlineMedium)

        // Test 1: Health Check (No Auth)
        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = apiService.healthCheck()
                        healthResult = if (response.isSuccessful) {
                            "✅ Success: ${response.body()}"
                        } else {
                            "❌ Error ${response.code()}: ${response.message()}"
                        }
                    } catch (e: Exception) {
                        healthResult = "❌ Exception: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Test Health (Public)")
        }
        Text(healthResult, style = MaterialTheme.typography.bodySmall)

        Divider()

        // Test 2: Auth Test (Protected)
        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = apiService.testAuth()
                        authResult = if (response.isSuccessful) {
                            "✅ Success: ${response.body()}"
                        } else {
                            "❌ Error ${response.code()}: ${response.message()}"
                        }
                    } catch (e: Exception) {
                        authResult = "❌ Exception: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Test Auth (Protected)")
        }
        Text(authResult, style = MaterialTheme.typography.bodySmall)

        Divider()

        // Test 3: Get Current User (Protected)
        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = apiService.getCurrentUser()
                        userResult = if (response.isSuccessful) {
                            "✅ Success: ${response.body()}"
                        } else {
                            "❌ Error ${response.code()}: ${response.message()}"
                        }
                    } catch (e: Exception) {
                        userResult = "❌ Exception: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Current User")
        }
        Text(userResult, style = MaterialTheme.typography.bodySmall)

        Divider()

        // Test 4: Full Integration (Protected)
        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = apiService.fullIntegrationTest()
                        integrationResult = if (response.isSuccessful) {
                            "✅ Success: ${response.body()}"
                        } else {
                            "❌ Error ${response.code()}: ${response.message()}"
                        }
                    } catch (e: Exception) {
                        integrationResult = "❌ Exception: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Full Integration Test")
        }
        Text(integrationResult, style = MaterialTheme.typography.bodySmall)
    }
}