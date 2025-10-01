package com.example.intellecta.chatBot

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intellecta.R
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPage(modifier : Modifier = Modifier) {

    val viewModel: ChatViewModel = koinViewModel()
    Scaffold(

        topBar = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            ){
            TopAppBar(
                title = {

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center) {

                                Icon(
                                    painter = painterResource(R.drawable.depth_5__frame_0__2_),
                                    contentDescription = "Bot",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text("ChatMate", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                                    color = Color.Black)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(Color(0xFFB8D89D))



            )
        }}
    ) { innerPadding->

        Column(modifier = Modifier
            .padding(innerPadding)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(0.9f), // Start color
                        Color(0xFFB8D89D)
                    )
                )
            )) {
            
            MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
            MessageInput(onMessageSend = {
                viewModel.sendMessage(it)

            })

        }

    }
}



@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(onMessageSend:(String)-> Unit)
{

    var message by remember {
        mutableStateOf("")}

    Row(modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

         ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = {message = it},
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Color.Blue,    // Label color when focused
                unfocusedLabelColor = Color.Blue)


        )
        IconButton(onClick = {
            if(message.isNotBlank())
            onMessageSend(message)
             message = ""}) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                modifier = Modifier.size(30.dp)
            )
        }

    }
}

@Composable
fun MessageList(modifier:  Modifier = Modifier, messageList: List<MessageModel>){
    if(messageList.isEmpty()){
        Column(modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Icon(
                modifier = Modifier.size(50.dp),
                painter = painterResource(R.drawable.outline_attachment_24),
                contentDescription = "ask",
                tint = Color.Gray

            )
            SelectionContainer {  Text(text = "Ask me Anything", fontSize = 24.sp) }

        }

    }else{
    LazyColumn (modifier = modifier,
        reverseLayout = true){
        items(messageList.reversed()){
            MessageRow(messageModel = it)


        }
    }}


}

@Composable
fun MessageRow(messageModel:MessageModel) {
    val isModel = messageModel.role == "model"

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isModel) {
            Icon(
                painter = painterResource(R.drawable.outline_sticky_note_2_24),
                contentDescription = "bot",
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isModel) MaterialTheme.colorScheme.inverseOnSurface else Color(
                            0xFFB8D89D
                        )
                    )
                    .padding(16.dp)


            ) {


                    SelectionContainer {
                        Text(
                            text = messageModel.message,
                            fontWeight = FontWeight.W500

                        )
                    }
                }


        }
    }
}








