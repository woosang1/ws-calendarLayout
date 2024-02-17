package com.example.ws_calendarlayout.calendar.common

import com.example.ws_calendarlayout.R
import java.util.Date

data class CalendarData(
    val checkIn: Date,
    val checkOut: Date,
    val titleTextResource: TextResource,
    val daysOfTheWeekTextResource: TextResource,
    val daysTextResource: TextResource
)

data class TextResource(
    val size: Int = 10,
    val color: Int = R.color.black,
    val font: Int? = null
)