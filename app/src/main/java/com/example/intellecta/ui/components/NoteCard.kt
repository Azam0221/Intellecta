package com.example.intellecta.ui.components


import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aibrain.ui.theme.surfaceContainerLight
import com.example.intellecta.R

@Composable
fun NoteCard(title:String, description : String){
    Column {
        Row(modifier = Modifier.fillMaxWidth()){
            Column (modifier = Modifier.padding(top = 4.dp)) {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(44.dp)
                        .background(color = surfaceContainerLight, shape = RoundedCornerShape(6.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_sticky_note_2_24),
                        contentDescription = "Notes"
                    )
                }
            }

            Spacer(modifier = Modifier.padding(horizontal = 12.dp))

            Column() {

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = description,
                    color = Color.Gray
                )

                Text(
                    text = description,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column (modifier = Modifier.padding(top = 16.dp)) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black.copy(alpha = 0.7f),
                    contentDescription = "Right arrow"
                )
            }


        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}