package ru.sinjvf.emotions.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.sinjvf.emotions.presentation.navigation.BottomNavigationBar
import ru.sinjvf.emotions.presentation.navigation.MainNavHost

@Composable
fun MainActivityContent() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        MainNavHost(navController = navController, paddingValues = it)
    }
}