package com.example.intellecta.ui.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.R
import com.example.intellecta.fileManaging.FileType
import com.example.intellecta.fileManaging.getFileNameFromUri
import com.example.intellecta.model.AttachmentsOption
import com.example.intellecta.navigation.Screens
import com.example.intellecta.ui.components.AttachmentsOptionBox
import com.example.intellecta.ui.components.FileCard
import com.example.intellecta.ui.components.NewAttachmentsCard
import com.example.intellecta.ui.components.NewNoteCard
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotePage(navCtrl:NavHostController){

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

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),


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
                text = "Add Note",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.weight(1f))

            Box()
            {
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

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        }

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        OutlinedTextField(
            value = uiState.note.title,
            onValueChange = { newValue ->
                viewModel.updateNoteField { it.copy(title = newValue)}
            },
            placeholder = { Text("Title", color = MaterialTheme.colorScheme.onSurface) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.tertiary,
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                 focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            )
        )

        // Content Field
        OutlinedTextField(
            value = uiState.note.content,
            onValueChange = { newValue ->
                viewModel.updateNoteField { it.copy(content = newValue)}
            },
            placeholder = { Text("Content",
                color = MaterialTheme.colorScheme.onSurface) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.tertiary,
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))


        Text(text = "Attachments",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 14.dp),
            color = MaterialTheme.colorScheme.onSurface)

        Spacer(modifier = Modifier.height(16.dp))

        if(uiState.attachedFiles.isEmpty()){
            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(
                    painter = painterResource(R.drawable.outline_attachment_24),
                    contentDescription = "attachments",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = "No attachments added",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        else {

            LazyColumn() {
                items(uiState.attachedFiles) { file ->
                    NewAttachmentsCard(
                        imageRes = when (file.type) {
                            FileType.AUDIO -> R.drawable.outline_keyboard_voice_24
                            FileType.IMAGE -> R.drawable.outline_image_24
                            FileType.DOCUMENT -> R.drawable.outline_sticky_note_2_24
                        },
                        type = file.uri.lastPathSegment ?: "File",
                        name = file.displayName,
                        onClick = { viewModel.removeFile(file.uri, file.type) },
                    )
                }
            }
        }


        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {  viewModel.saveNote() },

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 20.dp),
            // 5. Customize the button's colors.
            colors = ButtonDefaults.buttonColors(
                   containerColor = MaterialTheme.colorScheme.primaryContainer,
                   contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Save Note",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                Icon(
                    painter = painterResource(R.drawable.outline_save_24),
                    contentDescription = "Save",
                    tint = MaterialTheme.colorScheme.onSurface
                )

            }
        }


    }
}