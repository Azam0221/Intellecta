package com.example.intellecta.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intellecta.R

@Composable
fun NoteCardVer_2(title:String,
                  description : String,
                  onClick: () -> Unit,
                  onLongPress:() -> Unit,
                  attachmentCount: Int,
                  time:Long){

    val formattedTime = remember(time) {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
        sdf.format(java.util.Date(time))
    }

    Card(modifier = Modifier.fillMaxWidth()
        .height(140.dp)
        .pointerInput(Unit){
            detectTapGestures(
                onTap = {onClick()},
                onLongPress = {onLongPress()}
            )
        },
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(modifier = Modifier.padding(12.dp)){

            Row(verticalAlignment = Alignment.CenterVertically){
                Text(text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(R.drawable.outline_attachment_24),
                    contentDescription = "attachment count",
                    modifier = Modifier.size(24.dp)
                        .padding(vertical = 4.dp)

                )
                Text(text = attachmentCount.toString())
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))

            Text(text = formattedTime,
                color = Color.Gray,)

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis, // add "..." if text is longer
                color = Color.Gray,
                )
        }

    }

    Spacer(modifier = Modifier.padding(vertical = 12.dp))

}