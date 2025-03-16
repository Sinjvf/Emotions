package ru.sinjvf.emotions.domain

import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic
import ru.sinjvf.emotions.data.entries.Event
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ExportPdf  @Inject constructor():ExportToFile {

    override fun exportEvents(
        context: Context,
        events: List<Event>,
        emotions: List<Emotion>,
        emotionCharacteristic: List<EmotionCharacteristic>
    ): Uri? {
        // Создаем карту для быстрого поиска эмоции по ID
        val emotionMap = emotions.associateBy { it.id }
        val characteristicMap = emotionCharacteristic.associateBy { it.id }

        // Создаем PDF-документ
        val document = PdfDocument()

        // Параметры страницы (альбомная ориентация)
        val pageWidth = 842 // Ширина A4 в альбомной ориентации (842pt)
        val pageHeight = 595 // Высота A4 в альбомной ориентации (595pt)
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // Начинаем создание страницы
        val page = document.startPage(pageInfo)
        var canvas = page.canvas

        // Настройки текста
        val textPaint = Paint().apply {
            color = Color(0, 0, 0).toArgb()
            textSize = 12f
        }

        // Отступы и начальные координаты
        val margin = 50
        var currentY = margin

        // Группируем события по датам
        val groupedEvents = events.groupBy { event ->
            val date = Date(event.timestamp)
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
        }

        // Рисуем события на странице
        groupedEvents.forEach { (date, eventsForDay) ->
            // Рисуем дату
            canvas.drawText("Дата: $date", margin.toFloat(), currentY.toFloat(), textPaint)
            currentY += 20

            eventsForDay.forEach { event ->
                val emotion = emotionMap[event.emotionId]
                val characteristic = characteristicMap[emotion?.characteristicId]

                // Определяем цвет фона
                val backgroundColor = when (emotion?.characteristicId) {
                    1L -> Color(0, 255, 0) // good
                    2L -> Color(255, 0, 0) // bad
                    3L -> Color(0, 0, 255) // neutral
                    else -> Color(255, 255, 255)
                }
                val alpha = (event.strength * 0.1f)

                // Преобразуем Compose Color в Int и применяем прозрачность
                val backgroundColorInt = backgroundColor.toArgb()
                val alphaInt = (alpha * 255).toInt()

                // Рисуем фон строки
                val backgroundPaint = Paint().apply {
                    color = backgroundColorInt
                    this.alpha = alphaInt // Устанавливаем прозрачность
                }

                canvas.drawRect(
                    margin.toFloat(),
                    currentY.toFloat(),
                    pageWidth - margin.toFloat(),
                    currentY + 20f,
                    backgroundPaint
                )

                // Рисуем текст события
                val eventText = buildString {
                    append("Эмоция: ${emotion?.name ?: "Неизвестно"}")
                    append(", Характеристика: ${characteristic?.name ?: "Неизвестно"}")
                    append(", Сила: ${event.strength}")
                    append(", Описание: ${event.description}")
                    append(", Мысль: ${event.thought}")
                    append(", Ощущения: ${event.sensation}")
                }
                canvas.drawText(eventText, margin.toFloat(), currentY + 15f, textPaint)
                currentY += 25

                // Если текст выходит за пределы страницы, создаем новую страницу
                if (currentY > pageHeight - margin) {
                    document.finishPage(page)
                    val newPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, document.pages.size + 1).create()
                    val newPage = document.startPage(newPageInfo)
                    canvas = newPage.canvas
                    currentY = margin
                }
            }
        }

        // Завершаем последнюю страницу
        document.finishPage(page)

        // Сохраняем PDF в файл
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "events.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        return uri?.also { fileUri ->
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(fileUri)
                outputStream?.use { stream ->
                    document.writeTo(stream)
                }
            } catch (e: Exception) {
                resolver.delete(fileUri, null, null) // Удаляем файл в случае ошибки
                throw e
            } finally {
                document.close()
            }
        }
    }

}