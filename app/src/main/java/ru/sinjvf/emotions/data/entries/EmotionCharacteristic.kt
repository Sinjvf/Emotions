package ru.sinjvf.emotions.data.entries

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotion_characteristics")
data class EmotionCharacteristic(
    @PrimaryKey(autoGenerate = false) val id: Long = 0,
    val name: String // Название субьективной характеристики (например, "плохая", "хорошая")
)