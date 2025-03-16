package ru.sinjvf.emotions.domain

import ru.sinjvf.emotions.data.dao.EmotionDao
import ru.sinjvf.emotions.data.dao.EventDao
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic
import ru.sinjvf.emotions.data.entries.Event
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Provider

suspend fun importEventsFromString(
    fileContent: String,
    eventDao: Provider<EventDao>,
    emotionDao: EmotionDao,
    emotions: List<Emotion>,
    emotionCharacteristics: List<EmotionCharacteristic>
) {
    // Очищаем таблицу событий перед импортом
    eventDao.get().deleteAllEvents()

    // Создаем карту для быстрого поиска эмоции по имени
    val emotionMap = emotions.associateBy { it.name }.toMutableMap()

    // Создаем карту для быстрого поиска характеристики по имени
    val characteristicMap = emotionCharacteristics.associateBy { it.name }

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    var currentDate: Long = 0

    // Чтение файла построчно
    fileContent.lineSequence().forEach { line ->
        if (line.isNotBlank()) {
            if (!line.startsWith("\t")) {
                // Это строка с датой
                val date = dateFormat.parse(line)
                currentDate = date?.time ?: 0
            } else {
                // Это строка с событием
                val parts = line.trim().split("\t")
                if (parts.size >= 3) {
                    val emotionName = parts[0]
                    val characteristicName = parts[1]
                    val strength = parts[2].toIntOrNull() ?: 0
                    val description = parts.getOrNull(3) ?: ""

                    // Находим эмоцию по имени
                    var emotion = emotionMap[emotionName]

                    // Если эмоция не найдена, добавляем её в базу данных
                    if (emotion == null) {
                        // Определяем характеристику эмоции (например, "neutral" по умолчанию)
                        val characteristic =
                            characteristicMap[characteristicName] ?: EmotionCharacteristic(name = "neutral", id = 3)

                        // Создаем новую эмоцию
                        emotion = Emotion(
                            name = emotionName,
                            characteristicId = characteristic.id
                        )

                        // Вставляем новую эмоцию в базу данных и получаем её ID
                        val emotionId = emotionDao.insert(emotion)
                        // Обновляем эмоцию с новым ID
                        emotion = emotion.copy(id = emotionId)
                        // Обновляем карту эмоций
                        emotionMap[emotionName] = emotion
                    }

                    // Если эмоция найдена или добавлена, создаем событие
                    val event = Event(
                        timestamp = currentDate,
                        emotionId = emotion.id,
                        strength = strength,
                        description = description,
                        thought = "",
                        sensation = ""
                    )
                    eventDao.get().insert(event)
                }
            }
        }
    }
}