package com.example.intellecta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.R
import com.example.intellecta.fileManaging.FileType
import com.example.intellecta.model.FileListItemCard
import com.example.intellecta.ui.components.FileStorageCard
import com.example.intellecta.ui.components.NewAttachmentsCard
import com.example.intellecta.viewmodel.FilesManagingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FileStoragePage(
    navCtrl : NavHostController
    ) {
    val viewModel: FilesManagingViewModel = koinViewModel()
    val uiState by viewModel.fileUIState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFile()

    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)

    ) {
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        Row(
            modifier = Modifier.padding(top = 12.dp).fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically,
            //  horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable { navCtrl.popBackStack() }
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "File Storage",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.baseline_menu_24),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        }

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        LazyColumn() {
            items(uiState.allFiles) { file ->
                FileStorageCard(fileMeta = file) {
                    viewModel.openFile(file)
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }


    }



    }