package com.example.intellecta.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aibrain.ui.theme.surfaceContainerLight
import com.example.intellecta.navigation.Screens
import com.example.intellecta.ui.components.RowButton

@Composable
fun HomeScreen(navCtrl :NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navCtrl.navigate(Screens.AddNoteScreen.route) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .padding(start = 12.dp, end = 12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier

                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    .alpha(1f)
            ) {

                Text(
                    text = "Intellecta",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    textDecoration = TextDecoration.None,
                    letterSpacing = 0.sp,
                    lineHeight = 23.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 120.dp),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                )

                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings"
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "Hello, Alex ",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,
                lineHeight = 23.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 4.dp)
                    .fillMaxWidth()
                    .alpha(1f),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
            )

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .alpha(1f)


            ) {
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(color = surfaceContainerLight)
                        .clip(RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
//                    Image(
//                        painter = painterResource(),
//                        contentDescription = "smiley",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.size(28.dp)
//                    )
                }


                Column(verticalArrangement = Arrangement.Top) {

                    Text(
                        text = "Current Emotion",
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                    )



                    Text(
                        text = "Feeling: Calm",
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }


            }


            Spacer(modifier = Modifier.padding(vertical = 4.dp))



            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier

                    .fillMaxWidth()
                    .height(64.dp)
            ) {

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
                ) {


                    Text(
                        text = "Quick Note",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        0.dp,
                        Alignment.CenterHorizontally
                    ),
                    modifier = Modifier

                        .width(130.dp)
                        .height(40.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp
                            )
                        )
                        .background(color = MaterialTheme.colorScheme.secondaryContainer.copy(0.3f))

                        .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)

                        .alpha(1f)
                ) {


                    Text(
                        text = "New Summary",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                }

            }

            Text(
                text = "Recent Summaries",
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            RowButton("NoteList") {navCtrl.navigate(Screens.NotesListScreen.route)}
        }


    }
}
