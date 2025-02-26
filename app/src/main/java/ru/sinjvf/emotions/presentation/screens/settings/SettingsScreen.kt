package ru.sinjvf.emotions.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    gotoImportExport: () -> Unit,
    gotoAddEmotion: () -> Unit,
) {
    // Список опций настроек
    val settingsOptions = listOf(
        "Импорт/Экспорт" to gotoImportExport,
        "Добавить эмоцию" to gotoAddEmotion,
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(settingsOptions) { option ->
            Text(
                text = option.first,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Переход на экран "Импорт/Экспорт"
                        option.second()
                    }
                    .padding(vertical = 16.dp)
            )
            Divider() // Разделитель между опциями
        }
    }
}