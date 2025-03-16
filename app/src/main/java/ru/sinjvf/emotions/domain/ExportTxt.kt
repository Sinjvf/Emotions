package ru.sinjvf.emotions.domain

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic
import ru.sinjvf.emotions.data.entries.Event
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ExportTxt @Inject constructor() : ExportToFile {
    override fun exportEvents(
        context: Context,
        events: List<Event>,
        emotions: List<Emotion>,
        emotionCharacteristic: List<EmotionCharacteristic>
    ): Uri? {
        // Создаем карту для быстрого поиска эмоции по ID
        val emotionMap = emotions.associateBy { it.id }
        val characteristicMap = emotionCharacteristic.associateBy { it.id }

        // Группируем события по датам
        val groupedEvents = events.groupBy { event ->
            val date = Date(event.timestamp)
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
        }

        // Создаем строку для записи в файл
        val fileContent = buildString {
            groupedEvents.forEach { (date, eventsForDay) ->
                appendLine(date) // Записываем дату
                eventsForDay.forEach { event ->
                    val emotion = emotionMap[event.emotionId]
                    val characteristic = characteristicMap[emotion?.characteristicId]
                    if (emotion != null && characteristic != null) {
                        appendLine(
                            """
                        \t${emotion.name}
                        \t${characteristic.name}
                        \t${event.strength}
                        \t${event.description}
                        \t${event.thought}
                        \t${event.sensation}
                        """.trimIndent()
                        )
                    }
                }
            }
        }

        // Создаем файл в папке Documents
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "events.txt")
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        return uri?.also { fileUri ->
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(fileUri)
                outputStream?.use { stream ->
                    stream.write(fileContent.toByteArray())
                }
            } catch (e: Exception) {
                resolver.delete(fileUri, null, null) // Удаляем файл в случае ошибки
                throw e
            }
        }
    }
}