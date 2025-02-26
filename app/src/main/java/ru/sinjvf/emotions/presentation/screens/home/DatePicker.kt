package ru.sinjvf.emotions.presentation.screens.home

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DatePicker(
    selectedDate: Long = System.currentTimeMillis(),
    onDateSelected: (Long) -> Unit
) {
    val context = LocalContext.current

    // Форматирование даты
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(selectedDate))
    // Обертка для текстового поля
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Открыть DatePicker по клику на поле
                showDatePicker(context) { newDate ->
                    onDateSelected(newDate)
                }
            }
    ) {
        OutlinedTextField(
            value = formattedDate,
            onValueChange = {}, // Поле только для чтения
            label = { Text("Дата") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            enabled = false // Отключаем возможность фокусировки
        )
    }
}

// Функция для отображения DatePicker
fun showDatePicker(context: Context, onDateSelected: (Long) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(year, month, day)
            onDateSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}