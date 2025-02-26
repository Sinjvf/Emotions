package ru.sinjvf.emotions.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    goAddEmotions: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.loadEmotions()
    }
    // Получаем список эмоций из StateFlow
    val emotions by viewModel.emotions.collectAsState()

    AddUpdateEventScreen(
        doneButtonText = "Сохранить событие",
        action = { event -> viewModel.insertEvent(event) },
        emotions = emotions,
        goAddEmotions = goAddEmotions
    )
}

