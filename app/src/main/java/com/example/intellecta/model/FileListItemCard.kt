package com.example.intellecta.model

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.intellecta.R
import com.example.intellecta.fileManaging.FileType

@Composable
fun FileListItemCard(fileMeta: FileMeta,onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(
                id = when(fileMeta.fileType){
                    FileType.AUDIO.name -> R.drawable.outline_keyboard_voice_24
                    FileType.IMAGE.name -> R.drawable.outline_image_24
                    else -> R.drawable.outline_sticky_note_2_24
                }
            ),
            contentDescription = fileMeta.fileType,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.padding(horizontal = 12.dp))
        Column {
                Text(text = fileMeta.fileName, fontWeight = FontWeight.Bold)
                Text(text = "Type: ${fileMeta.fileType}", style = MaterialTheme.typography.bodySmall)
        }
    }
}