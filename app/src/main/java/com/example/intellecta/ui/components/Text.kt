package com.example.intellecta.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextComposable(
    text: String,
    textAlign: TextAlign,
    fontSize: Int,
    letterSpacing: Int,
    fontWeight: FontWeight,

    ){

    Text(
        text = text,
        textAlign = textAlign,
        fontSize = fontSize.sp,
        letterSpacing =letterSpacing.sp ,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier,
        fontWeight = fontWeight,

    )

}