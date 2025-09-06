package com.example.intellecta.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aibrain.ui.theme.surfaceContainerLight
import com.example.intellecta.R

@Composable
fun NoteCard(title:String, description : String,onClick: () -> Unit,onLongPress:() -> Unit){

    Column {
        Row(modifier = Modifier.fillMaxWidth()
            .pointerInput(Unit){
                detectTapGestures(
                    onTap = {onClick()},
                    onLongPress = {onLongPress()}
                )
            },
          ){
            Column (modifier = Modifier.padding(top = 8.dp)) {

                IconButton(onClick = {

                },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)))
                {
                    Icon(painter =  painterResource(R.drawable.outline_sticky_note_2_24),
                        contentDescription = "Voice")
                }
            }

            Spacer(modifier = Modifier.padding(horizontal = 12.dp))

            Column( modifier = Modifier.weight(1f)) {

                Text(
                    text = title,
                    maxLines = 1,
                    fontSize = 18.sp,
                )

                Text(
                    text = description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis, // add "..." if text is longer
                    color = Color.Gray,

                )
            }


            Column (modifier = Modifier.padding(top = 16.dp)) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    tint = Color.Black.copy(0.5f),
                    contentDescription = "Right arrow",
                    modifier = Modifier
                        .size(40.dp)
                )
            }


        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}