package ru.sinjvf.emotions.presentation.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sinjvf.emotions.data.dao.EmotionDao
import ru.sinjvf.emotions.data.dao.EventDao
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.Event
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventDao: EventDao,
    private val emotionsDao: EmotionDao
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> get() = _events

    private val _emotions = MutableStateFlow<List<Emotion>>(emptyList())
    val emotions: StateFlow<List<Emotion>> get() = _emotions

    fun loadEmotions() {
        viewModelScope.launch(Dispatchers.IO) {
            _emotions.value = emotionsDao.getAll() // Загружаем эмоции из базы данных
        }
    }

    fun loadEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _events.value = eventDao.getAll()
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            eventDao.delete(event)
            _events.value = eventDao.getAll()
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            eventDao.update(event)
        }
    }

    // Получаем эмоцию по ID
    suspend fun getEmotionById(emotionId: Long): Emotion? {
        return withContext(Dispatchers.IO) {
            emotionsDao.getById(emotionId) // Используем метод из EmotionDao
        }
    }
}