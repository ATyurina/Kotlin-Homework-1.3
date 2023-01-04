package ru.netology

import java.util.concurrent.TimeUnit

fun main() {
    val seconds = 720L
    val time = agoToText(seconds)
    println("Был(а) в сети $time")
}

const val MINUTE = 60
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun agoToText(seconds: Long): String {
    val timeAgo: String = when (seconds) {
        in 0 until MINUTE -> "только что"
        in MINUTE until HOUR -> getEnding(toMinute(seconds), "minutes")
        in HOUR until DAY -> getEnding(toHour(seconds), "hours")
        in DAY until 2 * DAY -> "вчера"
        in 2 * DAY until 3 * DAY -> "позавчера"
        else -> "давно"
    }
    return timeAgo
}

fun toMinute(localSeconds: Long): Long {
    return TimeUnit.SECONDS.toMinutes(localSeconds)
}
fun toHour(localSeconds: Long): Long {
    return TimeUnit.MINUTES.toHours(toMinute(localSeconds))
}

fun getEnding(time: Long, key: String): String {

    val regex = """([^1]|^)1$""".toRegex()
    val regexTwo = """([^1]|^)[2-4]$""".toRegex()

    val ending = when {
        regex.containsMatchIn(time.toString()) -> mapOf("minutes" to "$time минуту назад", "hours" to "$time час назад")
        regexTwo.containsMatchIn(time.toString()) -> mapOf("minutes" to "$time минуты назад", "hours" to "$time часа назад")
        else -> mapOf("minutes" to "$time минут назад", "hours" to "$time часов назад")
    }
    return ending[key].toString()
}