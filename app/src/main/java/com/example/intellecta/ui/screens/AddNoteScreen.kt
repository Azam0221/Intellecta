package com.example.intellecta.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intellecta.fileManaging.FileType
import com.example.intellecta.R
import com.example.intellecta.fileManaging.getFileNameFromUri
import com.example.intellecta.model.AttachmentsOption
import com.example.intellecta.ui.components.AttachmentsOptionBox
import com.example.intellecta.ui.components.FileCard
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen( navCtrl : NavHostController
) {
    val viewModel: NoteViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showOptions by remember { mutableStateOf(false) }

    // File pickers
    // 1. gallery/camera

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri : Uri? ->

        uri?.let{
            val fileName = getFileNameFromUri(context, it)
            viewModel.addFile(it, FileType.IMAGE, fileName)
        }
    }

    // 2. documents
    val docPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = getFileNameFromUri(context, it) // <-- Get the name
            viewModel.addFile(it, FileType.DOCUMENT, fileName)
        }
    }

    // 3. Voice
    val audioRecorderLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                val fileName = getFileNameFromUri(context, it)
                viewModel.addFile(it, FileType.AUDIO,fileName) }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearError() // <-- reset error so it won’t show again
        }
    }

   // val scrollState = rememberScrollState()

        Column(modifier = Modifier
            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.surfaceVariant.copy(0.7f))
            .padding(start=16.dp,end = 16.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                IconButton(
                    onClick = { navCtrl.popBackStack() },
                    interactionSource = remember { MutableInteractionSource() },
                ){
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Close",
                        )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(text = "New Note", fontWeight = Bold, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.weight(1f))

                Box {
                    IconButton(
                        onClick = { showOptions = true },
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_attachment_24),
                            contentDescription = "attachments",

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


//                IconButton(
//                    onClick = {   viewModel.saveNote()
//                        if (uiState.isSaved) {
//                            navCtrl.popBackStack()
//                        }
//                     },
//                    interactionSource = remember { MutableInteractionSource() },
//                ){
//                    Icon(imageVector = Icons.Outlined.Check, contentDescription = "Close",
//                    )
//                }


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
                    //containerColor = MaterialTheme.colorScheme.surface,
                    //focusedTextColor = MaterialTheme.colorScheme.onSurface,
                   // focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    //unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                    //focusedBorderColor = Color.Transparent,
                    //unfocusedBorderColor = Color.Transparent,
                    //disabledBorderColor = Color.Transparent,
                    //errorBorderColor = Color.Transparent
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

//           Row(modifier = Modifier.fillMaxWidth()){
//
//               AttachmentCard(R.drawable.outline_sticky_note_2_24,"file"){
//                   docPickerLauncher.launch(arrayOf("*/*"))
//               }
//               Spacer(modifier = Modifier.weight(1f))
//               AttachmentCard(R.drawable.outline_image_24,"image",){
//                   imagePickerLauncher.launch("image/*")
//               }
//               Spacer(modifier = Modifier.weight(1f))
//               AttachmentCard(R.drawable.outline_keyboard_voice_24,"voice",)
//               {audioRecorderLauncher.launch(Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION))}
//
//
//           }

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
                        name = file.displayName,
                        onClick = {viewModel.removeFile(file.uri,file.type)},
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {  viewModel.saveNote() },

                modifier = Modifier
                    .fillMaxWidth(),
                // 5. Customize the button's colors.
                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.attachmentCardBackground.copy(0.5f),
//                    contentColor = MaterialTheme.colorScheme.attachmentCardContent
                )
            ) {
                Text(text = "Save Note")
            }



    }
}