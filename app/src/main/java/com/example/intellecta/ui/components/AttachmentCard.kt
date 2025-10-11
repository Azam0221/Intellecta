package com.example.intellecta.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aibrain.ui.theme.attachmentCardBackground
import com.example.aibrain.ui.theme.attachmentCardContent
@Composable
fun AttachmentCard(
    @DrawableRes imageRes: Int,
    docType: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.attachmentCardBackground.copy(0.5f))
            .padding(horizontal = 12.dp), // padding inside the box
        contentAlignment = Alignment.Center, // align Row content to start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp) // spacing between icon & text
        ) {
            Icon(
                painter = painterResource(id = imageRes),
                contentDescription = docType,
                tint = MaterialTheme.colorScheme.attachmentCardContent
            )
            Text(
                text = docType,
                color = MaterialTheme.colorScheme.attachmentCardContent
            )
            Spacer(modifier= Modifier.padding(horizontal = 2.dp))
        }
    }
}
