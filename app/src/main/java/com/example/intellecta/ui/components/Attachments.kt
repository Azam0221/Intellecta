package com.example.intellecta.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intellecta.R

@Composable
fun Attachments(type:String, @DrawableRes imageRes:Int,onClick:()-> Unit){
    Column {
        Image(
            painter = painterResource(imageRes),
            contentDescription = type,
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = type,
            fontSize = 16.sp
            )
    }
}