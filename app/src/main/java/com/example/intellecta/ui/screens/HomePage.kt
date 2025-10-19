package com.example.intellecta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.R
import com.example.intellecta.navigation.Screens
import com.example.intellecta.ui.components.NewNoteCard
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePage(navCtrl:NavHostController){

    val viewModel: NoteViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Int?>(null) }

    var search by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadAllNotes()
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
                            painter = painterResource(R.drawable.baseline_menu_24),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "InTeLlecta",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(R.drawable.outline_settings_24),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))

            LazyVerticalStaggeredGrid(
                // 1. Set the number of columns to 2.
                columns = StaggeredGridCells.Fixed(2),
               // verticalItemSpacing = 10.dp,          // Use verticalItemSpacing
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)
            ) {
                // 5. Populate the grid with items from your list.
                items(uiState.notes) { note ->
                     NewNoteCard(
                        title = note.title,
                        description = note.content,
                        time = note.timeStamp,
                        attachmentCount = 2,
                        onClick = {
                            navCtrl.navigate(Screens.NoteDetailsScreen.createRoute(note.id))
                        },
                        onLongPress = {
                            noteToDelete = note.id
                            showDialog = true
                        }
                    )
                }
            }
        }

        if (showDialog && noteToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Delete Note") },
                text = { Text("Are you sure you want to delete this note?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteNote(noteToDelete!!)
                            showDialog = false
                        }
                    ) {
                        Text("Delete",
                            color = MaterialTheme.colorScheme.onSurface)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("cancel",
                            color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            )

    }
}