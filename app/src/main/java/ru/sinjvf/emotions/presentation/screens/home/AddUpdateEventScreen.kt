package ru.sinjvf.emotions.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.Event

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun AddUpdateEventScreen(
    doneButtonText: String,
    action: (Event) -> Unit,
    emotions: List<Emotion>,
    goAddEmotions: () -> Unit,
    event: Event? = null,
) {
    val emotionMap = emotions.associateBy { it.id }
    val emotion = event?.let { emotionMap[event.emotionId] }
    var selectedDate by remember { mutableStateOf(event?.timestamp ?: System.currentTimeMillis()) }
    var selectedEmotion by remember { mutableStateOf<Emotion?>(emotion) }
    var selectedStrength by remember { mutableStateOf(event?.strength ?: 0) }
    var description by remember { mutableStateOf(TextFieldValue(event?.description ?: "")) }
  //  selectedEmotion = emotion

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DatePicker(selectedDate = selectedDate, onDateSelected = { selectedDate = it })
        Spacer(modifier = Modifier.height(16.dp))

        // Выбор эмоции
        EmotionSelection(
            emotions = emotions,
            selectedEmotion = selectedEmotion,
            onEmotionSelected = { selectedEmotion = it },
            gotoAddEmotion = goAddEmotions
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Выбор силы эмоции
        Text("Сила эмоции", style = MaterialTheme.typography.titleMedium)
        Slider(
            value = selectedStrength.toFloat(),
            onValueChange = { selectedStrength = it.toInt() },
            valueRange = 0f..10f,
            steps = 9,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = if (selectedStrength == 0) "<1" else selectedStrength.toString(),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Контроллер для управления клавиатурой
        val keyboardController = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = description,
            onValueChange = { value -> description = value },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Закрываем клавиатуру при нажатии на Done
                    keyboardController?.hide()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка сохранения
        Button(
            onClick = {
                if (selectedEmotion != null) {
                    val newEvent = Event(
                        timestamp = selectedDate / 1000 * 1000, // Первая секунда дня
                        emotionId = selectedEmotion!!.id,
                        strength = selectedStrength,
                        description = description.text,
                        id = event?.id ?: 0
                    )
                    action(newEvent)

                    selectedEmotion = null // Очищаем выбранную эмоцию
                    selectedStrength = 0 // Очищаем силу эмоции
                    description = TextFieldValue("") // Очищаем описание
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedEmotion != null
        ) {
            Text(doneButtonText)
        }
    }
}
