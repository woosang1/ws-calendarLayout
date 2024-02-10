package com.example.ws_calendarlayout.calendar.common

import com.example.ws_calendarlayout.calendar.screen.item.common.CalendarItemState

data class ResourceData(
    val title: Text,
    val daysOfTheWeek: Text,
    val day: Text,
    val descriptionFont: Int?,
    val calendarItemState: CalendarItemState? = null
) {
    data class Text(
        val size: Int,
        val color: Int,
        val font: Int?
    )
}