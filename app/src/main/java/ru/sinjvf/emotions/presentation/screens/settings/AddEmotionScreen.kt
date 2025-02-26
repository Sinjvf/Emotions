package ru.sinjvf.emotions.presentation.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic

@Composable
fun AddEmotionScreen(
    viewModel: AddEmotionViewModel = hiltViewModel(),
    onBack: () -> Unit // Колбэк для возврата на предыдущий экран
) {
    LaunchedEffect(Unit) {
        viewModel.loadEmotionCharacteristics()
    }
    // Состояние для названия эмоции
    var emotionName by remember { mutableStateOf("") }

    // Состояние для выбранной характеристики
    var selectedCharacteristic by remember { mutableStateOf<EmotionCharacteristic?>(null) }

    // Состояние для отображения выпадающего списка
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // Список характеристик эмоций
    val characteristics by viewModel.characteristic.collectAsState()

    // Состояние для отображения сообщений
    var message by remember { mutableStateOf<String?>(null) }

    // CoroutineScope для вызова suspend-функций
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Поле ввода названия эмоции
        OutlinedTextField(
            value = emotionName,
            onValueChange = { emotionName = it },
            label = { Text("Название эмоции") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Выпадающий список для выбора характеристики
        Box(modifier = Modifier.fillMaxWidth()) {
            // Кнопка для открытия выпадающего списка
            OutlinedButton(
                onClick = { isDropdownExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedCharacteristic?.name ?: "Выберите характеристику")
            }

            // Выпадающий список
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                characteristics.forEach { characteristic ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCharacteristic = characteristic
                            isDropdownExpanded = false
                        },
                        text = { Text(characteristic.name) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка сохранения
        Button(
            onClick = {
                if (emotionName.isBlank() || selectedCharacteristic == null) {
                    message = "Заполните все поля"
                } else {
                    coroutineScope.launch {
                        viewModel.addEmotion(
                            name = emotionName,
                            characteristicId = selectedCharacteristic!!.id
                        )
                        message = "Эмоция добавлена!"
                        emotionName = "" // Очищаем поле ввода
                        selectedCharacteristic = null // Сбрасываем выбор
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Назад"
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }

        // Отображение сообщений
        if (message != null) {
            Text(
                text = message!!,
                color = if (message!!.startsWith("Заполните")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}