package ru.sinjvf.emotions.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.sinjvf.emotions.data.dao.EmotionCharacteristicDao
import ru.sinjvf.emotions.data.dao.EmotionDao
import ru.sinjvf.emotions.data.dao.EventDao
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic
import ru.sinjvf.emotions.data.entries.Event
import ru.sinjvf.emotions.data.prepopulate.PrepopulateDatabaseCallback

@Database(
    entities = [EmotionCharacteristic::class, Emotion::class, Event::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun emotionCharacteristicDao(): EmotionCharacteristicDao
    abstract fun emotionDao(): EmotionDao
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "emotion_tracker_database"
                )
                    .addCallback(
                        PrepopulateDatabaseCallback(
                            { INSTANCE!!.emotionCharacteristicDao() },
                            { INSTANCE!!.emotionDao() })
                    )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE events ADD COLUMN thought TEXT DEFAULT ''")
        database.execSQL("ALTER TABLE events ADD COLUMN sensation TEXT DEFAULT ''")
    }
}