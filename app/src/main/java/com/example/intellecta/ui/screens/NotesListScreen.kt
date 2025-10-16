package com.example.intellecta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.navigation.Screens
import com.example.intellecta.ui.components.NoteCardVer_2
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navCtrl:NavHostController) {

    val viewModel: NoteViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Int?>(null) }

    var search by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadAllNotes()
    }

    Scaffold(

    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.7f))
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier

                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    .alpha(1f)
            ) {

                Text(
                    text = "All Notes",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.sp,
                    lineHeight = 23.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 120.dp),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                )

                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add"
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { newText ->
                    search = newText
                },
                placeholder = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(uiState.notes) { note ->
                    NoteCardVer_2(
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
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("cancel")
                    }
                }
            )
        }
    }
}