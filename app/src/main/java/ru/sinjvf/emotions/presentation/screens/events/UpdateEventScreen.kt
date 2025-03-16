package ru.sinjvf.emotions.presentation.screens.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinjvf.emotions.data.entries.Event
import ru.sinjvf.emotions.presentation.screens.home.AddUpdateEventScreen

@Composable
fun UpdateEventScreen(
    viewModel: EventsViewModel = hiltViewModel(),
    event: Event,
    goAddEmotions: () -> Unit,
    goEvents: () -> Unit

) {
    LaunchedEffect(Unit) {
        viewModel.loadEmotions()
    }
    // Получаем список эмоций из StateFlow
    val emotions by viewModel.emotions.collectAsState()

    AddUpdateEventScreen(
        doneButtonText = "Редактировать событие",
        action = { actionEvent ->
            viewModel.updateEvent(actionEvent)
            goEvents()
        },
        emotions = emotions,
        goAddEmotions = goAddEmotions,
        event = event
    )
}

