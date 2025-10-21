package com.example.intellecta.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intellecta.chatBot.ChatPage
import com.example.intellecta.data.bottomDrawList
import com.example.intellecta.ui.screens.HomePage
import com.example.intellecta.ui.screens.ProfilePage
import com.example.intellecta.ui.screens.SearchPage

@Composable
fun BottomNavBar(navCtrl:NavHostController){


    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ){

                bottomDrawList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = {
                            Icon(painter = painterResource(navItem.icon), contentDescription = "Icon")
                        },
                        label = {
                            Text(text = navItem.label)
                        },

                    )
                }
            }
        },

        floatingActionButton = {
            if (selectedIndex == 0) {
                FloatingActionButton(
                    onClick = { navCtrl.navigate(Screens.AddNotePage.route) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(20.dp),
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add",
                    )
                 }
            }
        }
    ) { innerPadding ->
        // Pass the innerPadding to the content screen
        ContentBottomNavScreen(modifier = Modifier.padding(innerPadding), selectedIndex, navCtrl)
    }
}

@Composable
fun ContentBottomNavScreen(modifier: Modifier, selectedIndex: Int, navCtrl: NavHostController) {
    when (selectedIndex) {
        0 -> HomePage(navCtrl = navCtrl)
        1 -> SearchPage()
        2 -> ChatPage(navCtrl)
        3-> ProfilePage()
    }

}