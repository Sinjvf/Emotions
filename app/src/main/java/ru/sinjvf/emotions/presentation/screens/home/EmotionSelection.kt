package ru.sinjvf.emotions.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.sinjvf.emotions.data.entries.Emotion

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmotionSelection(
    emotions: List<Emotion>,
    selectedEmotion: Emotion?,
    onEmotionSelected: (Emotion) -> Unit,
    gotoAddEmotion: () -> Unit
) {
    // Состояние для текста поиска
    var searchText by remember { mutableStateOf("") }

    // Фильтруем эмоции по тексту поиска
    val filteredEmotions = emotions.filter { emotion ->
        emotion.name.contains(searchText, ignoreCase = true)
    }

    // Контроллер для управления клавиатурой
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Поле для быстрого поиска
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Поиск эмоции") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Закрываем клавиатуру при нажатии на Done
                    keyboardController?.hide()
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Заголовок
        Text("Выберите эмоцию", style = MaterialTheme.typography.titleMedium)

        // Список эмоций
        if (filteredEmotions.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                items(filteredEmotions) { emotion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEmotionSelected(emotion) } // Клик в любом месте строки
                            .padding(2.dp)
                    ) {
                        RadioButton(
                            selected = selectedEmotion?.id == emotion.id,
                            onClick = { onEmotionSelected(emotion) }, // Клик на RadioButton
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text(
                            text = emotion.name,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        } else {
            // Если эмоции не найдены, отображаем кнопку "Добавить эмоцию"
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Эмоция не найдена",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = gotoAddEmotion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Добавить эмоцию")
                }
            }
        }
    }
}