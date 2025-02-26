package ru.sinjvf.emotions.data.entries

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "emotions",
    foreignKeys = [
        ForeignKey(
            entity = EmotionCharacteristic::class,
            parentColumns = ["id"],
            childColumns = ["characteristicId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Emotion(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String, // Название эмоции (например, "Счастье", "Разочарование")
    val characteristicId: Long // Ссылка на характеристику эмоции
)