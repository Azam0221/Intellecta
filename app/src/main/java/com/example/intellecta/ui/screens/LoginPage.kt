package com.example.intellecta.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intellecta.R
import com.example.intellecta.model.AuthState
import com.example.intellecta.navigation.Screens
import com.example.intellecta.ui.components.TextFieldComponent
import com.example.intellecta.viewmodel.AuthViewModel
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navCtrl:NavHostController
){

    val viewModel: AuthViewModel = koinViewModel()
    val authState = viewModel.authState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Authenticated ->{
                // navCtrl.popBackStack()
                navCtrl.navigate(Screens.BottomNavScreen.route){
                    popUpTo(Screens.LoginPage.route){
                        inclusive = true
                    }
                }
            }
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }


    }

    var userEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)

    ) {
        Spacer(modifier = Modifier.padding(vertical = 30.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp,end = 20.dp),
            verticalAlignment = Alignment.CenterVertically){

            Column {
                Text(
                    text = "Login",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "to InTellecta",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.bot_2),
                contentDescription = "Bot",
                modifier = Modifier.size(70.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Box(
            modifier = Modifier.fillMaxSize()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)
                )
                .background( shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
                    color = MaterialTheme.colorScheme.tertiary
                ),


            ) {
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Text(text = "Email",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp))

                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                TextFieldComponent(userEmail) {userEmail = it }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Text(text = "Password",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp))

                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                TextFieldComponent(password) { password = it }

                Spacer(modifier = Modifier.padding(vertical = 12.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){

                    Button(
                        onClick = { viewModel.login(userEmail,password)},

                        modifier = Modifier
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        val annotatedString = buildAnnotatedString {

                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp
                                )
                            ) {
                                append("Create a new account? ")
                            }

                            pushStringAnnotation(tag = "SIGNUP", annotation = "signup")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("SignUp")
                            }
                            pop()
                        }

                        ClickableText(
                            text = annotatedString,
                            onClick = { offset ->
                                annotatedString.getStringAnnotations(tag = "SIGNUP", start = offset, end = offset)
                                    .firstOrNull()?.let {
                                        navCtrl.navigate(Screens.SignupPage.route)
                                        println("Login text clicked!")
                                        Log.d("SignUpClick", "Signup text clicked!")

                                    }
                            }
                        )

                    }


                Spacer(modifier = Modifier.padding(vertical = 8.dp))



                }

            }
        }
}
