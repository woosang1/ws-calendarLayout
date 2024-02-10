package com.example.ws_calendarlayout.calendar.common

sealed class CalendarSideEffect : SideEffect {

    data class ClearScreen(val indexList: ArrayList<Int?>): CalendarSideEffect()
    object RefreshScreen : CalendarSideEffect()

}