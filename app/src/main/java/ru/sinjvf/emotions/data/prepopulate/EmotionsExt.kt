package ru.sinjvf.emotions.data.prepopulate

import ru.sinjvf.emotions.data.entries.Emotion

val popularEmotions = listOf(
    Emotion(name = "счастье", characteristicId = 1),
    Emotion(name = "радость", characteristicId = 1),
    Emotion(name = "удовлетворение", characteristicId = 1),
    Emotion(name = "восторг", characteristicId = 1),
    Emotion(name = "любовь", characteristicId = 1),
    Emotion(name = "гордость", characteristicId = 1),
    Emotion(name = "облегчение", characteristicId = 1),
    Emotion(name = "благодарность", characteristicId = 1),
    Emotion(name = "вдохновение", characteristicId = 1),
    Emotion(name = "оптимизм", characteristicId = 1),
    Emotion(name = "умиротворение", characteristicId = 1),
    Emotion(name = "восхищение", characteristicId = 1),
    Emotion(name = "энтузиазм", characteristicId = 1),
    Emotion(name = "привязанность", characteristicId = 1),
    Emotion(name = "нежность", characteristicId = 1),
    Emotion(name = "доверие", characteristicId = 1),
    Emotion(name = "уверенность", characteristicId = 1),
    Emotion(name = "удовольствие", characteristicId = 1),
    Emotion(name = "воодушевление", characteristicId = 1),
    Emotion(name = "сопереживание", characteristicId = 1),
    Emotion(name = "признательность", characteristicId = 1),
    Emotion(name = "влюбленность", characteristicId = 1),
    Emotion(name = "веселье", characteristicId = 1),
    Emotion(name = "эйфория", characteristicId = 1),
    Emotion(name = "надежда", characteristicId = 1),
    Emotion(name = "принятие", characteristicId = 1),
    Emotion(name = "уважение", characteristicId = 1),
    Emotion(name = "сочувствие", characteristicId = 1),
    Emotion(name = "теплота", characteristicId = 1),
    Emotion(name = "увлеченность", characteristicId = 1),
    Emotion(name = "вдохновленность", characteristicId = 1),
    Emotion(name = "расслабленность", characteristicId = 1),
    Emotion(name = "предвкушение", characteristicId = 1),
    Emotion(name = "умиление", characteristicId = 1),
    Emotion(name = "блаженство", characteristicId = 1),
    Emotion(name = "гармония", characteristicId = 1),

    Emotion(name = "ненависть", characteristicId = 2),
    Emotion(name = "злость", characteristicId = 2),
    Emotion(name = "грусть", characteristicId = 2),
    Emotion(name = "гнев", characteristicId = 2),
    Emotion(name = "страх", characteristicId = 2),
    Emotion(name = "отвращение", characteristicId = 2),
    Emotion(name = "тревога", characteristicId = 2),
    Emotion(name = "стыд", characteristicId = 2),
    Emotion(name = "вина", characteristicId = 2),
    Emotion(name = "разочарование", characteristicId = 2),
    Emotion(name = "обида", characteristicId = 2),
    Emotion(name = "ревность", characteristicId = 2),
    Emotion(name = "зависть", characteristicId = 2),
    Emotion(name = "одиночество", characteristicId = 2),
    Emotion(name = "отчаяние", characteristicId = 2),
    Emotion(name = "беспомощность", characteristicId = 2),
    Emotion(name = "уныние", characteristicId = 2),
    Emotion(name = "апатия", characteristicId = 2),
    Emotion(name = "раздражение", characteristicId = 2),
    Emotion(name = "неприязнь", characteristicId = 2),
    Emotion(name = "недовольство", characteristicId = 2),
    Emotion(name = "неуверенность", characteristicId = 2),
    Emotion(name = "смущение", characteristicId = 2),
    Emotion(name = "неловкость", characteristicId = 2),
    Emotion(name = "неприятие", characteristicId = 2),
    Emotion(name = "сожаление", characteristicId = 2),
    Emotion(name = "печаль", characteristicId = 2),
    Emotion(name = "тоска", characteristicId = 2),
    Emotion(name = "беспокойство", characteristicId = 2),
    Emotion(name = "паника", characteristicId = 2),

    Emotion(name = "удивление", characteristicId = 3),
    Emotion(name = "любопытство", characteristicId = 3),
    Emotion(name = "интерес", characteristicId = 3),
    Emotion(name = "безразличие", characteristicId = 3),
    Emotion(name = "нейтральность", characteristicId = 3),
    Emotion(name = "равнодушие", characteristicId = 3),
    Emotion(name = "сосредоточенность", characteristicId = 3),
    Emotion(name = "внимательность", characteristicId = 3),
    Emotion(name = "размышление", characteristicId = 3),
    Emotion(name = "созерцание", characteristicId = 3),
    Emotion(name = "медитация", characteristicId = 3),
    Emotion(name = "рефлексия", characteristicId = 3),
    Emotion(name = "осознанность", characteristicId = 3),
    Emotion(name = "вовлеченность", characteristicId = 3),
    Emotion(name = "наблюдение", characteristicId = 3),
    Emotion(name = "раздумье", characteristicId = 3),
    Emotion(name = "созерцательность", characteristicId = 3),
    Emotion(name = "мечтательность", characteristicId = 3),
    Emotion(name = "рефлексивность", characteristicId = 3),
    Emotion(name = "осознание", characteristicId = 3),
    Emotion(name = "внимание", characteristicId = 3),
)

val customEmotions = listOf(
    Emotion(name = "тревога", characteristicId = 2), // bad
    Emotion(name = "гордыня", characteristicId = 2), // bad
    Emotion(name = "сожаление", characteristicId = 2), // bad
    Emotion(name = "спокойствие", characteristicId = 3), // neutral
    Emotion(name = "удовлетворение", characteristicId = 1), // good
    Emotion(name = "гордость", characteristicId = 1), // good
    Emotion(name = "облегчение", characteristicId = 1), // good
    Emotion(name = "дзен", characteristicId = 1), // good
    Emotion(name = "эмпатия", characteristicId = 1), // good
    Emotion(name = "неприятие", characteristicId = 2), // bad
    Emotion(name = "неловкость", characteristicId = 2), // bad
    Emotion(name = "восторг", characteristicId = 1), // good
    Emotion(name = "разочарование", characteristicId = 2), // bad
    Emotion(name = "упорство", characteristicId = 1), // good
    Emotion(name = "предвкушение", characteristicId = 1), // good
    Emotion(name = "волнение", characteristicId = 1), // good
    Emotion(name = "радость", characteristicId = 1), // good
    Emotion(name = "кайф", characteristicId = 1), // good
    Emotion(name = "рациональность", characteristicId = 1), // good
    Emotion(name = "воображляндия", characteristicId = 3), // neutral
    Emotion(name = "благодарность", characteristicId = 1), // good
    Emotion(name = "зависимость", characteristicId = 2), // bad
    Emotion(name = "скука", characteristicId = 2), // bad
    Emotion(name = "вовлеченность", characteristicId = 3), // neutral
    Emotion(name = "избегание", characteristicId = 2), // bad
    Emotion(name = "отчуждённость", characteristicId = 2), // bad
    Emotion(name = "тупняк", characteristicId = 3) // neutral
)

// Объединяем списки и удаляем дубликаты
val allEmotions = (customEmotions + popularEmotions).distinctBy { it.name }