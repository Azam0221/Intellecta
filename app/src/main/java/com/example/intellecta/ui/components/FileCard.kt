package com.example.intellecta.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.intellecta.R

@Composable
fun FileCard(
    @DrawableRes imageRes : Int,
    type : String,
    onClick : () -> Unit
){
    Column(modifier = Modifier
        .background(color =MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = RoundedCornerShape(12.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        Row(
            modifier = Modifier
                .width(200.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Box(modifier = Modifier
                .padding()
                .size(40.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.8f),),
                contentAlignment = Alignment.Center )
            {
                Icon(painter =  painterResource(R.drawable.outline_sticky_note_2_24),
                    contentDescription = "Voice",
                    modifier = Modifier
                        .size(30.dp),
                   // tint = MaterialTheme.colorScheme.primary

                    )
            }


            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Text(
                text = type,
                modifier = Modifier.padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = "clear",
                modifier = Modifier.size(35.dp)
                    .padding(top=6.dp, end= 4.dp)
                    .clickable { onClick() }
            )
        }


    }
    Spacer(modifier = Modifier.padding(horizontal = 12.dp))
}