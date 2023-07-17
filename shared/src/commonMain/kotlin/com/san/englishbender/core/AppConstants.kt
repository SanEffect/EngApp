package com.san.englishbender.core

object AppConstants {

    const val DATABASE_NAME = "english_bender"

    const val WORDS_API_URL = "https://wordsapiv1.p.rapidapi.com"
    //        const val WORDS_API_KEY = "API_KEY"
    const val WORDS_API_KEY = "a76e4d09a9msh2c7ac1797e0784bp157a8cjsn10a314ec13a7"
    const val WORDS_API_HOST = "wordsapiv1.p.rapidapi.com"

    // Google Translate Api Key
//    const val TRANSLATE_API_HOST = "http://translation.googleapis.com/language/translate/v2/"
    const val TRANSLATE_API_HOST = "https://translation.googleapis.com/"
    const val TRANSLATE_API_KEY = "AIzaSyD_Im3yvSqfVpN9aBa2rzmfKIsE5L_2Jks"

    const val RECORD_MAX_LENGTH_TITLE = 40
    const val RECORD_MAX_LENGTH_DESCRIPTION = 100

    val GREETINGS = listOf(
        "What's up?",
        "Please tell me something",
        "Please share your thoughts with me",
        "Hello there!",
        "What's new?",
        "Good to see you again",
        "How are you?",
        "How are you doing?",
        "Howdy!",
        "Hiya!",
        "Look, who’s here!",
    )
}