package com.example.intellecta.chatBot

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.R
import com.example.intellecta.navigation.Screens
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPage(navCtrl:NavHostController) {

    val viewModel: ChatViewModel = koinViewModel()


        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)

        ) {
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 8.dp), // Apply horizontal padding to the whole Row
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // This is the key change
            ) {
               // Spacer(modifier = Modifier.padding(2.dp))
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable { navCtrl.popBackStack() }
                )



                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "InTeLlecta Assistant",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Icon(
                        painter = painterResource(R.drawable.bot_2),
                        contentDescription = "bot",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
            }

            Spacer(modifier = Modifier.padding(vertical = 12.dp))


            MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
            MessageInput(onMessageSend = {
                viewModel.sendMessage(it)

            })

        }
}



@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(onMessageSend:(String)-> Unit)
{

    var message by remember {
        mutableStateOf("")}

    Row(modifier = Modifier.fillMaxWidth().
        background(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp), color =  MaterialTheme.colorScheme.secondary )
        .padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
         ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = {message = it},
            placeholder = ({ Text(text= "Type here", color = MaterialTheme.colorScheme.onSurface) }),
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            )


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
                painter = painterResource(R.drawable.bot_2),
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
                painter = painterResource(R.drawable.bot),
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
                        if (isModel) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiary.copy(0.5f)
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
        if (!isModel.equals("model")) {
            Icon(
                painter = painterResource(R.drawable.outline_person_24),
                contentDescription = "person",
                modifier = Modifier.padding(start = 4.dp)
            )
        }

    }
}








