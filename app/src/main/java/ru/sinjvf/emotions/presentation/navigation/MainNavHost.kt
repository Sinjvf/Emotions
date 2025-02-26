package ru.sinjvf.emotions.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.sinjvf.emotions.data.entries.Event
import ru.sinjvf.emotions.presentation.screens.events.EventsScreen
import ru.sinjvf.emotions.presentation.screens.events.UpdateEventScreen
import ru.sinjvf.emotions.presentation.screens.home.HomeScreen
import ru.sinjvf.emotions.presentation.screens.settings.AddEmotionScreen
import ru.sinjvf.emotions.presentation.screens.settings.ImportExportScreen
import ru.sinjvf.emotions.presentation.screens.settings.SettingsScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Events.route) { EventsScreen { event -> navigateUpdateEvent(event, navController) } }
        composable(Screen.Home.route) { HomeScreen { navController.navigate(addEmotionRoute) } }
        composable(Screen.Settings.route) {
            SettingsScreen(
                { navController.navigate(importExportRoute) },
                { navController.navigate(addEmotionRoute) }
            )
        }
        composable(importExportRoute) { ImportExportScreen() }
        composable(addEmotionRoute) { AddEmotionScreen { navController.popBackStack() } }

        composable(
            route = "$updateEventRoute/{event}",
            /* arguments = listOf(
                 navArgument("event") {
                     type = NavType.ParcelableType<Event>(Event::class.java)
                 }
             )*/
        ) { backStackEntry ->
            /* val event = backStackEntry.arguments?.getParcelable<Event>("event")
             if (event != null) {*/
            UpdateEventScreen(
                goAddEmotions = { navController.navigate(addEmotionRoute) },
                goEvents = { navController.navigate(Screen.Events.route) })
            // }
        }
    }
}

fun navigateUpdateEvent(event: Event, navController: NavHostController) {
    navController.navigate("$updateEventRoute/${event}")
}

const val importExportRoute = "import_export"
const val addEmotionRoute = "add_emotion"
const val updateEventRoute = "update_event/{event}"