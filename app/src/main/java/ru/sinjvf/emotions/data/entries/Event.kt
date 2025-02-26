package ru.sinjvf.emotions.data.entries

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = Emotion::class,
            parentColumns = ["id"],
            childColumns = ["emotionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long, // Таймстемп даты события
    val emotionId: Long, // Ссылка на эмоцию
    val strength: Int, // Сила эмоции (например, от 1 до 10)
    val description: String // Описание события
)