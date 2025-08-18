package com.example.intellecta.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldEntry(type : String,value:String,onValueChange:(String) -> Unit){

    Column {
        Text(text = type + "*",
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 12.dp))

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(20.dp),

            textStyle = TextStyle(
                fontSize = 12.sp
            ),
            )
    }

}


@Composable
fun PasswordTextField(type: String,value:String,onValueChange:(String) -> Unit) {
    Column {
        Text(
            text = type + "*",
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Row{
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                textStyle = TextStyle(
                    fontSize = 12.sp
                ),
            )

            Spacer(modifier = Modifier.weight(1f))



        }
    }

}


