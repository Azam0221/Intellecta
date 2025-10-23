
package com.example.intellecta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.R
import com.example.intellecta.fileManaging.FileType
import com.example.intellecta.navigation.Screens
import com.example.intellecta.ui.components.NewAttachmentsCard
import com.example.intellecta.ui.components.NewAttachmentsCard_2
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailPage(noteId:Int, navCtrl:NavHostController){



    val viewModel: NoteViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()



    LaunchedEffect(Unit){
        viewModel.loadNoteForEdit(noteId)
        viewModel.loadFileForNote()
    }

    val time = uiState.note.timeStamp
    val formattedTime = remember(time) {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
        sdf.format(java.util.Date(time))
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
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable { navCtrl.popBackStack() }
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Note Details",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.outline_edit_24),
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(30.dp).clickable { navCtrl.navigate(Screens.EditNotePage.createRoute(noteId)) }
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Box(
            modifier = Modifier.fillMaxSize()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)
                )
                .background( shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
                    color = MaterialTheme.colorScheme.tertiary
                    ),


        ) {
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = uiState.note.title,
                        maxLines = 3,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis, // Add this line
                        modifier = Modifier.weight(1f) // Move the weight here
                    )

                    Icon(
                        painter = painterResource(R.drawable.outline_attachment_24),
                        contentDescription = "attachment count",
                        modifier = Modifier.size(30.dp)
                            .padding(vertical = 4.dp),
                        tint = MaterialTheme.colorScheme.onSurface

                    )
                    Text(
                        text = uiState.attachedFiles.size.toString(),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Text(
                    text = formattedTime,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = uiState.note.content,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TextStyle(
                        lineBreak = LineBreak.Paragraph
                    )
                )


                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Divider()

                Spacer(modifier = Modifier.padding(vertical = 4.dp))



                Text(
                    text = "Attachments",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))


            if (uiState.attachedFiles.isEmpty()) {
                Spacer(modifier = Modifier.height(100.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
            } else {

                LazyColumn() {
                    items(uiState.attachedFiles) { file ->
                        NewAttachmentsCard_2(
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
                    onClick = {   },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( bottom = 20.dp),
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

                        Icon(
                            painter = painterResource(R.drawable.outline_wb_sunny_24),
                            contentDescription = "Summarize",
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                        Text(
                            text = "Summarize Note",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )




                    }
                }


        }

        }

    }


}