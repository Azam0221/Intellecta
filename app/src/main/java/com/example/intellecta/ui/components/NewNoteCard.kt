package com.example.intellecta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.R

@Composable
fun NewNoteCard(
                title:String,
                description : String,
                onClick: () -> Unit,
                onLongPress:() -> Unit,
                attachmentCount: Int,
                time:Long
){
    val formattedTime = remember(time) {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
        sdf.format(java.util.Date(time))
    }

    Card(
        modifier = Modifier.padding(8.dp)
            .clickable {onClick() }
            .pointerInput(Unit){
                detectTapGestures(
                    onTap = {onClick()},
                    onLongPress = {onLongPress()}
                )
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(12.dp)
    ){
        Column(
            modifier = Modifier.padding(14.dp)
                .background(color = MaterialTheme.colorScheme.tertiary)
        ){
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = title,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(R.drawable.outline_attachment_24),
                    contentDescription = "attachment count",
                    modifier = Modifier.size(24.dp)
                        .padding(vertical = 4.dp),
                    tint = MaterialTheme.colorScheme.onSurface

                )
                Text(text = attachmentCount.toString(),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = description,
                overflow = TextOverflow.Ellipsis, // Add this line
                maxLines = 6,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    lineBreak = LineBreak.Paragraph
            )
            )
            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                text = formattedTime,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}