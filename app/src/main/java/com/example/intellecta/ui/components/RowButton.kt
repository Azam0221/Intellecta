package com.example.intellecta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowButton(name: String, onClick:() -> Unit){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            0.dp,
            Alignment.CenterHorizontally
        ),
        modifier = Modifier
            .width(112.dp)
            .height(40.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)
            .alpha(1f)
            .clickable { onClick() }
    ) {


        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
        )

    }

}