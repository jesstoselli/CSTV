package dev.jessto.desafiocstv.utils.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAmount
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun getYesterday(): Date {
    val today = LocalDateTime.now()

    return Date.from(today.minusDays(-1)
        .atZone(ZoneId.systemDefault())
        .toInstant())

}
