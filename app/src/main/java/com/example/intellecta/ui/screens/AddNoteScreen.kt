package com.example.intellecta.ui.screens

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.material3.Switch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.intellecta.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(

) {
    val scrollState = rememberScrollState()
    Scaffold() { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
            .verticalScroll(scrollState)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }

                Spacer(modifier = Modifier.padding(horizontal = 50.dp))

                Text(text = "Add Note", fontWeight = Bold, style = MaterialTheme.typography.titleLarge)

            }


            Spacer(modifier = Modifier.height(16.dp))

            // Title Field
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F6F8)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Content Field
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFF5F6F8)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Voice, Image, Attach Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {}) {
                   // Icon(painter = painterResource(R.drawable.outline_mic_none_24), contentDescription = "Voice")
                }
                Text("Voice")

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = {}) {
                   // Icon(painter = painterResource(R.drawable.outline_image_24), contentDescription = "Image")
                }
                Text("Image")

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(onClick = {}) {
                  //  Icon(painter = painterResource(R.drawable.outline_attach_file_24), contentDescription = "Attach")
                }
                Text("Attach")
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enable AI Summary")
                Switch(
                    checked = false,
                    onCheckedChange = {true}
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                    modifier = Modifier

                        .width(90.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
                        .background(color = MaterialTheme.colorScheme.secondaryContainer.copy(0.3f))

                        .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)

                        .alpha(1f)
                ) {


                    Text(
                        text = "Cancel",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                }



                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                    modifier = Modifier

                        .width(80.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)
                        .alpha(1f)
                ) {


                    Text(
                        text = "Save",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                    )

                }



            }

        }
    }
}