package com.example.intellecta.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.FileType
import com.example.intellecta.R
import com.example.intellecta.model.AttachmentsOption
import com.example.intellecta.ui.components.AttachmentsOptionBox
import com.example.intellecta.ui.components.FileCard
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(noteId : Int , navCtrl: NavHostController){

    val viewModel: NoteViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.loadNote(noteId)
    }

    var showOptions by remember { mutableStateOf(false) }

    // File pickers
    // 1. gallery/camera

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri : Uri? ->
        uri?.let{viewModel.addFile(it, FileType.IMAGE)}
    }

    // 2. documents
    val docPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { viewModel.addFile(it, FileType.DOCUMENT) }
    }

    // 3. Voice
    val audioRecorderLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let { viewModel.addFile(it, FileType.AUDIO) }
        }
    }

    // val scrollState = rememberScrollState()
    Scaffold(

    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(start=16.dp,end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navCtrl.popBackStack() },
                    interactionSource = remember { MutableInteractionSource() },
                ){
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close",
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 40.dp))

                Text(text = "Add Note", fontWeight = Bold, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.weight(1f))

                Box {
                    IconButton(
                        onClick = { showOptions = true },
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_attachment_24),
                            contentDescription = "attachments",
                            modifier = Modifier.size(30.dp),

                            )
                    }

                    AttachmentsOptionBox(
                        showOption = showOptions,
                        onDismiss = { showOptions = false },
                        options = listOf(
                            AttachmentsOption("Voice", R.drawable.outline_keyboard_voice_24) {
                                audioRecorderLauncher.launch(Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION))
                            },
                            AttachmentsOption("Gallery", R.drawable.outline_image_24) {
                                imagePickerLauncher.launch("image/*")
                            },
                            AttachmentsOption("Files", R.drawable.outline_attach_file_24) {
                                docPickerLauncher.launch(arrayOf("*/*"))
                            }
                        )
                    )
                }


                IconButton(
                    onClick = {   viewModel.updateNote(uiState.note)
                        if (uiState.isSaved) {
                            navCtrl.popBackStack()
                        }
                    },
                    interactionSource = remember { MutableInteractionSource() },
                ){
                    Icon(imageVector = Icons.Outlined.Check, contentDescription = "Close",
                        modifier = Modifier.size(30.dp),
                    )
                }


            }


            Spacer(modifier = Modifier.height(16.dp))

            // Title Field
            OutlinedTextField(
                value = uiState.note.title,
                onValueChange = { newValue ->
                    viewModel.updateNoteField { it.copy(title = newValue)}
                },
                placeholder = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5f),
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Content Field
            OutlinedTextField(
                value = uiState.note.content,
                onValueChange = { newValue ->
                    viewModel.updateNoteField { it.copy(content = newValue)}
                },
                placeholder = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5f),
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enable AI Summary")
                Switch(
                    checked = false,
                    onCheckedChange = {true}
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Attachments",
                fontWeight = Bold,
                fontSize = 18.sp)

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(){
                items(uiState.attachedFiles){ file->
                    FileCard(
                        imageRes = when (file.type) {
                            FileType.AUDIO -> R.drawable.outline_keyboard_voice_24
                            FileType.IMAGE -> R.drawable.outline_image_24
                            FileType.DOCUMENT -> R.drawable.outline_sticky_note_2_24
                        },
                        type = file.uri.lastPathSegment ?: "File",
                        onClick = {viewModel.removeFile(file.uri,file.type)},
                    )
                }
            }


        }
    }
}