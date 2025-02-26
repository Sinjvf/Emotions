package ru.sinjvf.emotions.presentation.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ImportExportScreen(
    viewModel: ImportExportViewModel = hiltViewModel()
) {
    // Состояние для отображения сообщений пользователю
    var message by remember { mutableStateOf("") }

    // Лончер для выбора файла
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(), // Используем OpenDocument
        onResult = { uri ->
            if (uri != null) {
                viewModel.import(uri) { message = it }
            }
        }
    )
    // Лончер для создания файла
    val fileCreatorLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain"), // Используем CreateDocument
        onResult = { uri ->
            if (uri != null) {
                viewModel.export { message = it }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Кнопка "Импорт"
        Button(
            onClick = { filePickerLauncher.launch(arrayOf("text/plain")) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Импорт")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Экспорт"
        Button(
            onClick = { fileCreatorLauncher.launch("events.txt") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Экспорт")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение сообщений
        Text(
            text = message,
            color = if (message.startsWith("Ошибка")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )

    }
}