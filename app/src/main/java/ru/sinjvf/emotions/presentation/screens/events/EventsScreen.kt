package ru.sinjvf.emotions.presentation.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EventsScreen(
    viewModel: EventsViewModel = hiltViewModel(),
    gotoUpdateScreen: (Event) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadEvents()
    }
    val events by viewModel.events.collectAsState()

    // Группируем события по дням
    val groupedEvents = events.groupBy { event ->
        val date = Date(event.timestamp)
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
    }

    // Отображаем таблицу событий
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        groupedEvents.forEach { (date, eventsForDay) ->
            item {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(eventsForDay) { event ->
                EventRow(event = event, viewModel = viewModel, gotoUpdateScreen = gotoUpdateScreen)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventRow(
    event: Event, viewModel: EventsViewModel,
    gotoUpdateScreen: (Event) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // Получаем эмоцию по event.emotionId (предполагаем, что у нас есть доступ к списку эмоций)
    val emotion by produceState<Emotion?>(initialValue = null) {
        viewModel.viewModelScope.launch {
            value = viewModel.getEmotionById(event.emotionId)
        }
    }

    // Вычисляем цвет фона и прозрачность
    val backgroundColor = when (emotion?.characteristicId) {
        1L -> Color(0, 255, 0) // good
        2L -> Color(255, 0, 0) // bad
        3L -> Color(0, 0, 255) // neutral
        else -> Color.Transparent
    }
    val alpha = (event.strength * 0.1f)

    // Отображаем строку события
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor.copy(alpha = alpha))
            .padding(8.dp)
            .clickable { showDialog = true }
    ) {
        Text(
            text = emotion?.name ?: "Неизвестно",
            modifier = Modifier
                .padding(4.dp)
                .width(120.dp)
        )
        Text(
            text = if (event.strength == 0) "<1" else event.strength.toString(),
            modifier = Modifier
                .padding(4.dp)
                .width(20.dp)
        )
        Text(
            text = event.description,
            modifier = Modifier.padding(4.dp)
        )
    }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = { showDialog = false },
            content = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White), // Белый фон для Surface
                    color = Color.White // Убедимся, что фон белый
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Выберите действие",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Что вы хотите сделать с этим событием?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {/*
                            Button(
                                onClick = {
                                    showDialog = false
                                    gotoUpdateScreen(event)
                                }
                            ) {
                                Text("Редактировать")
                            }
                            Spacer(modifier = Modifier.width(8.dp))*/
                            Button(
                                onClick = {
                                    showDialog = false
                                    viewModel.deleteEvent(event)
                                }
                            ) {
                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        )
    }
}