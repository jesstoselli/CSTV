package dev.jessto.desafiocstv.utils.mappers

import java.text.SimpleDateFormat
import java.util.*

private fun buildFromDate(from: Date): Calendar {
    val c = Calendar.getInstance()
    c.time = from
    return c
}
fun getYesterday(): Date {
    val today = Date()

    val c: Calendar = buildFromDate(today)
    c.add(Calendar.DATE, -1) // number of days to add
    return c.time
}

//fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
//    val formatter = SimpleDateFormat(format, locale)
//    return formatter.format(this)
//}
//
//fun getCurrentDateTime(): Date {
//    return Calendar.getInstance().time
//}
