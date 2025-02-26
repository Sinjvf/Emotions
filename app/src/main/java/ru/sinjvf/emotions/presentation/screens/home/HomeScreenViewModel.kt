package ru.sinjvf.emotions.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sinjvf.emotions.data.dao.EmotionDao
import ru.sinjvf.emotions.data.dao.EventDao
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.Event
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val eventDao: EventDao,
    private val emotionsDao: EmotionDao
) : ViewModel() {

    private val _emotions = MutableStateFlow<List<Emotion>>(emptyList())
    val emotions: StateFlow<List<Emotion>> get() = _emotions

    fun loadEmotions() {
        viewModelScope.launch(Dispatchers.IO) {
            _emotions.value = emotionsDao.getAll() // Загружаем эмоции из базы данных
        }
    }

    // Вставляем событие в базу данных
    fun insertEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) { // Запрос выполняется в фоновом потоке
            eventDao.insert(event)
        }
    }
}