package com.example.intellecta.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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

@Composable
fun NewAttachmentsCard(
    @DrawableRes imageRes : Int,
    type: String,
    name: String,
    onClick : () -> Unit
){

    Row(
        modifier = Modifier.padding(start = 12.dp,end =12.dp).fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.tertiary),
        verticalAlignment = Alignment.CenterVertically,
        //  horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Icon(painter =  painterResource(imageRes),
                contentDescription = type,
                tint = MaterialTheme.colorScheme.onSurface
            )

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        Text(
            text = name,
            overflow = TextOverflow.Ellipsis, // Add this line
            modifier = Modifier.weight(1f), // Move the weight here
            color = MaterialTheme.colorScheme.onSurface
        )

        Icon(
            imageVector = Icons.Outlined.Clear,
            contentDescription = "clear",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding( end= 12.dp)
                .clickable { onClick() }
        )
    }

    Spacer(modifier = Modifier.padding(vertical = 8.dp))
}