package ru.sinjvf.emotions.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.sinjvf.emotions.data.entries.Emotion

@Dao
interface EmotionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emotion: Emotion): Long

    @Query("SELECT * FROM emotions")
    suspend fun getAll(): List<Emotion>

    @Query("SELECT * FROM emotions WHERE id = :emotionId")
    suspend fun getById(emotionId: Long): Emotion?
}