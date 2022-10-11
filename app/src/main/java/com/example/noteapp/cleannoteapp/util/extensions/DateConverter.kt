package com.example.noteapp.cleannoteapp.util.extensions

import java.util.*

fun Date.appDate(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this

    val dayOfWeek: String = intDayToString(calendar.get(Calendar.DAY_OF_WEEK))
    val monthDay: String = calendar.get(Calendar.DAY_OF_MONTH).toString()
    val month: String = intMonthLongToString(calendar.get(Calendar.MONTH))
    val year: String = calendar.get(Calendar.YEAR).toString()

    return "$dayOfWeek, $monthDay $month"
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