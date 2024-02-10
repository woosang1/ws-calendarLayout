package com.example.ws_calendarlayout.calendar.common

sealed class CalendarSideEffect : SideEffect {

    data class ClearScreen(val indexList: ArrayList<Int?>): CalendarSideEffect()
    data class ShowBottomText(val title: String): CalendarSideEffect()
    object RefreshScreen : CalendarSideEffect()

}