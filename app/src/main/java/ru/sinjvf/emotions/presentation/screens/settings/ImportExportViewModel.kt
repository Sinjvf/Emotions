package ru.sinjvf.emotions.presentation.screens.settings

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sinjvf.emotions.data.dao.EmotionCharacteristicDao
import ru.sinjvf.emotions.data.dao.EmotionDao
import ru.sinjvf.emotions.data.dao.EventDao
import ru.sinjvf.emotions.domain.ExportPdf
import ru.sinjvf.emotions.domain.ExportToFile
import ru.sinjvf.emotions.domain.ExportTxt
import ru.sinjvf.emotions.domain.importEventsFromString
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class ImportExportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exportPdf: ExportPdf,
    private val exportTxt: ExportTxt,
    private val eventDao: Provider<EventDao>,
    private val emotionDao: EmotionDao,
    private val emotionCharacteristicDao: EmotionCharacteristicDao
) : ViewModel() {
    fun import(uri: Uri, onComplete: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Чтение файла из Uri
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                if (inputStream == null) {
                    onComplete("Ошибка: не удалось открыть файл")
                    return@launch
                }

                // Чтение содержимого файла
                val fileContent = inputStream.bufferedReader().use { it.readText() }

                // Получаем данные из базы данных
                val emotions = emotionDao.getAll()
                val characteristics = emotionCharacteristicDao.getAll()

                // Импорт данных из файла
                importEventsFromString(
                    fileContent = fileContent,
                    eventDao = eventDao,
                    emotionDao = emotionDao,
                    emotions = emotions,
                    emotionCharacteristics = characteristics
                )

                onComplete("Импорт завершен успешно!")
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete("Ошибка при импорте: ${e.message}")
            }
        }
    }

    fun exportToTxt(onComplete: (String) -> Unit,){
        export(onComplete, exportTxt)
    }
    fun exportToPdf(onComplete: (String) -> Unit,){
        export(onComplete, exportPdf)
    }

    private fun export(onComplete: (String) -> Unit, export:ExportToFile) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val events = eventDao.get().getAll()
                val emotions = emotionDao.getAll()
                val characteristics = emotionCharacteristicDao.getAll()

                val fileUri = export.exportEvents(
                    context = context,
                    events = events,
                    emotions = emotions,
                    emotionCharacteristic = characteristics
                )

                if (fileUri != null) {
                    onComplete("Экспорт завершен успешно! Файл сохранен в Documents.")
                } else {
                    onComplete("Ошибка: не удалось создать файл.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete("Ошибка при экспорте: ${e.message}")
            }
        }
    }
}