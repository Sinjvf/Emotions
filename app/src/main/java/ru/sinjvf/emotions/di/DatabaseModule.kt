package ru.sinjvf.emotions.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sinjvf.emotions.data.AppDatabase
import ru.sinjvf.emotions.data.dao.EmotionCharacteristicDao
import ru.sinjvf.emotions.data.dao.EmotionDao
import ru.sinjvf.emotions.data.dao.EventDao
import ru.sinjvf.emotions.domain.ExportPdf
import ru.sinjvf.emotions.domain.ExportTxt
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideEventDao(database: AppDatabase): EventDao {
        return database.eventDao()
    }

    @Provides
    @Singleton
    fun provideEmotionsDao(database: AppDatabase): EmotionDao {
        return database.emotionDao()
    }

    @Provides
    @Singleton
    fun provideEmotionsCharDao(database: AppDatabase): EmotionCharacteristicDao {
        return database.emotionCharacteristicDao()
    }

    @Provides
    @Singleton
    fun exportPdf(): ExportPdf {
        return ExportPdf()
    }

    @Provides
    @Singleton
    fun exportTxt(): ExportTxt {
        return ExportTxt()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}