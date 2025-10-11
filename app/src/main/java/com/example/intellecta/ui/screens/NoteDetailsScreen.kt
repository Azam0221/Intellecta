package com.example.intellecta.ui.screens

import android.content.Intent
import android.provider.MediaStore
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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aibrain.ui.theme.attachmentCardBackground
import com.example.aibrain.ui.theme.attachmentCardContent
import com.example.intellecta.R
import com.example.intellecta.model.AttachmentsOption
import com.example.intellecta.navigation.Screens
import com.example.intellecta.ui.components.AttachmentsOptionBox
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteDetailsScreen(noteId:Int,navCtrl: NavHostController){

    val viewModel: NoteViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.loadNoteForEdit(noteId)
    }


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
                    onClick = { navCtrl.popBackStack()},
                    modifier = Modifier.size(25.dp),
                    interactionSource = remember { MutableInteractionSource() },
                ){
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Close",
                    )
                }
                Spacer(modifier = Modifier.weight(1f))


                Text(text = "Note Details", fontWeight = Bold, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.weight(1f))


                IconButton(
                    onClick = { navCtrl.navigate(Screens.EditNoteScreen.createRoute(noteId))},
                    interactionSource = remember { MutableInteractionSource() },
                ){
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit"
                    )
                }


            }

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = uiState.note.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(text = uiState.note.timeStamp.toString(),
                color = Color.Gray,
                fontSize = 12.sp
                )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(text = uiState.note.content,
            )
            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Text(text = "Attachments", fontWeight = Bold, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = { },

                modifier = Modifier
                    .fillMaxWidth(),
                // 5. Customize the button's colors.
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.attachmentCardBackground.copy(0.5f),
                    contentColor = MaterialTheme.colorScheme.attachmentCardContent
                )
            ) {
                Text(text = "Summarize with AI")
            }
        }


}