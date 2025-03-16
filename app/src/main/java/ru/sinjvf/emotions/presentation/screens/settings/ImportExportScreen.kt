package ru.sinjvf.emotions.presentation.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

    // Лончер для выбора файла (импорт)
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(), // Используем OpenDocument
        onResult = { uri ->
            if (uri != null) {
                viewModel.import(uri) { message = it }
            }
        }
    )

    // Лончер для создания TXT-файла (экспорт TXT)
    val txtFileCreatorLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain"), // Используем CreateDocument
        onResult = { uri ->
            if (uri != null) {
                viewModel.exportToTxt { message = it }
            }
        }
    )

    // Лончер для создания PDF-файла (экспорт PDF)
    val pdfFileCreatorLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf"), // Используем CreateDocument
        onResult = { uri ->
            if (uri != null) {
                viewModel.exportToPdf { message = it }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Кликабельное текстовое поле для импорта
        ClickableTextField(
            text = "Импорт данных",
            onClick = { filePickerLauncher.launch(arrayOf("text/plain")) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кликабельное текстовое поле для экспорта в TXT
        ClickableTextField(
            text = "Экспорт в TXT",
            onClick = { txtFileCreatorLauncher.launch("events.txt") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кликабельное текстовое поле для экспорта в PDF
        ClickableTextField(
            text = "Экспорт в PDF",
            onClick = { pdfFileCreatorLauncher.launch("events.pdf") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение сообщений
        Text(
            text = message,
            color = if (message.startsWith("Ошибка")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Компонент для кликабельного текстового поля
@Composable
fun ClickableTextField(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}