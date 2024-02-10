package com.example.ws_calendarlayout.calendar.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar?.equalCalendar(otherCalendar: Calendar?): Boolean {
    if (this == null || otherCalendar == null) return false

    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH).plus(1)
    val day = this.get(Calendar.DAY_OF_MONTH)

    val year2 = otherCalendar.get(Calendar.YEAR)
    val month2 = otherCalendar.get(Calendar.MONTH).plus(1)
    val day2 = otherCalendar.get(Calendar.DAY_OF_MONTH)

    return ((year == year2) && (month == month2) && (day == day2))
}

fun getCalendarFormatter(calendar : Calendar, format: String) : String{
    val calendarViewFormat = SimpleDateFormat(format, Locale.KOREA)
    return calendarViewFormat.format(calendar.time)
}

fun calculateDateDifference(calendarA: Calendar, calendarB: Calendar): Long {
    // 두 Calendar 객체 간의 시간 차이를 밀리초로 얻음
    val timeInMillisA = calendarA.timeInMillis
    val timeInMillisB = calendarB.timeInMillis

    // 두 시간을 밀리초로 뺀 후, 밀리초를 일(day)로 변환
    val differenceInMillis = timeInMillisA - timeInMillisB
    val differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24)

    return differenceInDays
}