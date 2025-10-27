package com.example.intellecta.ui.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.intellecta.R
import com.example.intellecta.fileManaging.FileType
import com.example.intellecta.model.FileMeta

@Composable
fun FileStorageCard(
    fileMeta: FileMeta, onClick: () -> Unit
) {


    Row(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp).fillMaxWidth()
            .clickable { onClick() }
            .height(55.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.tertiary),
        verticalAlignment = Alignment.CenterVertically,
        //  horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        Icon(
            painter = painterResource(
                id = when (fileMeta.fileType) {
                    FileType.AUDIO.name -> R.drawable.outline_keyboard_voice_24
                    FileType.IMAGE.name -> R.drawable.outline_image_24
                    else -> R.drawable.outline_sticky_note_2_24
                }
            ),
            contentDescription = fileMeta.fileType,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center){
            Text(
                text = fileMeta.fileName,
                  overflow = TextOverflow.Ellipsis, // Add  this line
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Type: ${fileMeta.fileType}", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }


        Spacer(modifier = Modifier.padding(vertical = 8.dp))
    }
}