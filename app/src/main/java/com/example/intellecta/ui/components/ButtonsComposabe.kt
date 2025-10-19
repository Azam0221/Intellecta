package com.example.intellecta.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intellecta.theme.tertiaryLight



@Composable
fun Button(buttonName:String, onClick: () -> Unit){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = tertiaryLight
            // contentColor = if (isSelected) Color.Black else Color.DarkGray
        )// Equal width
    )
    {
        Text(text = buttonName,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp)

    }
}