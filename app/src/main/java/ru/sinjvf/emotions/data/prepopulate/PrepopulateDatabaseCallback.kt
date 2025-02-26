package ru.sinjvf.emotions.data.prepopulate

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sinjvf.emotions.data.dao.EmotionCharacteristicDao
import ru.sinjvf.emotions.data.dao.EmotionDao

class PrepopulateDatabaseCallback(

    private val emotionCharacteristicDao: () -> EmotionCharacteristicDao,
    private val emotionDao: () -> EmotionDao
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            // Получаем DAO через лямбду
            val emotionCharacteristic = emotionCharacteristicDao()
            // Вставляем предзаполненные данные
            allCharacteristics.forEach { emotionCharacteristic.insert(it) }

            // Получаем DAO через лямбду
            val dao = emotionDao()
            // Вставляем предзаполненные данные
            allEmotions.forEach { dao.insert(it) }
        }
    }
}