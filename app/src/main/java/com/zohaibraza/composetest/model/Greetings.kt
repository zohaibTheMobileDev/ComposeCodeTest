package com.zohaibraza.composetest.model

/**
 * [Greetings] will holds the greetings text based on current hour.
 * */
enum class Greetings(val message: String) {
    HELLO("Hello"),
    MORNING("Good Morning"),
    AFTERNOON("Good Afternoon"),
    EVENING("Good Evening"),
    NIGHT("Good Night"),
}