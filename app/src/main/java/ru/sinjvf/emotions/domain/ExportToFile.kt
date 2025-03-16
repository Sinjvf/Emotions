package ru.sinjvf.emotions.domain

import android.content.Context
import android.net.Uri
import ru.sinjvf.emotions.data.entries.Emotion
import ru.sinjvf.emotions.data.entries.EmotionCharacteristic
import ru.sinjvf.emotions.data.entries.Event

interface ExportToFile {
    fun exportEvents(
        context: Context,
        events: List<Event>,
        emotions: List<Emotion>,
        emotionCharacteristic: List<EmotionCharacteristic>
    ): Uri?
}