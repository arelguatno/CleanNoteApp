package com.example.noteapp.cleannoteapp.util.extensions

import java.util.*

fun Date.appMainFormat(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this

    val dayOfWeek: String = intDayToString(calendar.get(Calendar.DAY_OF_WEEK))
    val monthDay: String = calendar.get(Calendar.DAY_OF_MONTH).toString()
    val month: String = intMonthLongToString(calendar.get(Calendar.MONTH))
    calendar.get(Calendar.YEAR).toString()

    return "$dayOfWeek, $monthDay $month"
}

fun Date.appMainFormatWithTime(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this

    val dayOfWeek: String = intDayToString(calendar.get(Calendar.DAY_OF_WEEK))
    val monthDay: String = calendar.get(Calendar.DAY_OF_MONTH).toString()
    val month: String = intMonthLongToString(calendar.get(Calendar.MONTH))
    val hourOfDay: String = calendar.get(Calendar.HOUR).toString()
    val minutes: String = calendar.get(Calendar.MINUTE).toString().minutes()
    val amPM: String = calendar.get(Calendar.AM_PM).toString().amPm()
    return "$dayOfWeek, $monthDay $month $hourOfDay:$minutes $amPM"
}

fun String.amPm(): String {
    return if (this == "0") {
        "am"
    } else {
        "pm"
    }
}

fun String.minutes(): String {
    return if (this.toInt() <= 9) {
        "0$this"
    } else {
        this
    }
}

fun intDayToString(param: Int): String {
    return when (param) {
        Calendar.MONDAY -> "Mon"
        Calendar.TUESDAY -> "Tues"
        Calendar.WEDNESDAY -> "Wed"
        Calendar.THURSDAY -> "Thu"
        Calendar.FRIDAY -> "Fri"
        Calendar.SATURDAY -> "Sat"
        Calendar.SUNDAY -> "Sun"
        else -> "Sunday"
    }
}

fun intMonthLongToString(param: Int): String = when (param) {
    Calendar.JANUARY -> "January"
    Calendar.FEBRUARY -> "February"
    Calendar.MARCH -> "March"
    Calendar.APRIL -> "April"
    Calendar.MAY -> "May"
    Calendar.JUNE -> "June"
    Calendar.JULY -> "July"
    Calendar.AUGUST -> "August"
    Calendar.SEPTEMBER -> "September"
    Calendar.OCTOBER -> "October"
    Calendar.NOVEMBER -> "November"
    Calendar.DECEMBER -> "December"
    else -> "Sunday"
}