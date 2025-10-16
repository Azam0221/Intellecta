package com.example.intellecta.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intellecta.model.FileListItemCard
import com.example.intellecta.viewmodel.FilesManagingViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllFilesScreen(
    navCtrl : NavHostController
){
    val viewModel: FilesManagingViewModel = koinViewModel()
    val uiState by viewModel.fileUIState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.loadFile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Attached Files") },
                navigationIcon = {
                    IconButton(onClick = {navCtrl.popBackStack()}) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,"Back")
                    }
                }
            )
        }
    ){ innerPadding ->

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }

            }
            uiState.allFiles.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text("No Files Founded")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ){
                    items(uiState.allFiles) { file ->
                        FileListItemCard(fileMeta = file) {
                            viewModel.openFile(file)
                        }
                        Divider()
                    }
                }
            }

        }

    }

}