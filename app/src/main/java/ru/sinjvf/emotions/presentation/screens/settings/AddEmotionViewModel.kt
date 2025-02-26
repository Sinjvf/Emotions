package ru.sinjvf.emotions.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sinjvf.emotions.data.dao.EmotionCharacteristicDao
import ru.sinjvf.emotions.data.dao.EmotionDao
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic
import javax.inject.Inject

@HiltViewModel
class AddEmotionViewModel @Inject constructor(
    private val emotionDao: EmotionDao,
    private val emotionCharacteristicDao: EmotionCharacteristicDao
) : ViewModel() {

    private val _characteristic = MutableStateFlow<List<EmotionCharacteristic>>(emptyList())
    val characteristic: StateFlow<List<EmotionCharacteristic>> get() = _characteristic

    fun loadEmotionCharacteristics() {
        viewModelScope.launch(Dispatchers.IO) {
            _characteristic.value = emotionCharacteristicDao.getAll()
        }
    }

    fun addEmotion(name: String, characteristicId: Long) {
        viewModelScope.launch {
            val emotion = Emotion(
                name = name,
                characteristicId = characteristicId
            )
            emotionDao.insert(emotion)
        }
    }
}