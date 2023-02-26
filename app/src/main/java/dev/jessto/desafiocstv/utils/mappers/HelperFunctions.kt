package dev.jessto.desafiocstv.utils.mappers

import android.icu.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

fun getYesterday(): Date {
    val today = LocalDateTime.now()

    return Date.from(today.minusDays(-1)
        .atZone(ZoneId.systemDefault())
        .toInstant())
}

fun String.toFirstCapitalLetters(): String {
    return this.substring(0, 1).uppercase(Locale.getDefault()) + this.substring(1)
}

fun formatDate(date: Date) : String {
    val currentDate = date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();

    var datePattern = ""
    val now = LocalDate.now().atStartOfDay()

    val period = Math.abs(ChronoUnit.DAYS.between(currentDate, now))

    when {
        period == 0L -> {
            datePattern = "'Hoje,' HH:mm"
        }
        period in 0..6 -> {
            datePattern = "${DateFormat.ABBR_WEEKDAY}', ' HH:mm"
        }
        period > 7 -> {
            datePattern = "dd.MM HH:mm"
        }
    }

    val formatter = DateTimeFormatter.ofPattern(datePattern)
    return currentDate.format(formatter).toFirstCapitalLetters()
}

