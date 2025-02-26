package ru.sinjvf.emotions.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic

@Dao
interface EmotionCharacteristicDao {
    @Insert
    suspend fun insert(emotionCharacteristic: EmotionCharacteristic)

    @Query("SELECT * FROM emotion_characteristics")
    suspend fun getAll(): List<EmotionCharacteristic>
}