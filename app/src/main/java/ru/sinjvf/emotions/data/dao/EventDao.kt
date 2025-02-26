package ru.sinjvf.emotions.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.sinjvf.emotions.data.entries.Event

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event)

    @Query("SELECT * FROM events ORDER BY timestamp DESC")
    suspend fun getAll(): List<Event>

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()

    @Delete
    suspend fun delete(event: Event)

    @Update
    suspend fun update(event: Event) // Редактирование события по id
}