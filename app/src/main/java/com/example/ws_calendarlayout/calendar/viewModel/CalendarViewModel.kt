package com.example.ws_calendarlayout.calendar.viewModel

import com.example.ws_calendarlayout.base.OrbitViewModel
import com.example.ws_calendarlayout.calendar.common.CalendarSideEffect
import com.example.ws_calendarlayout.calendar.common.CalendarState
import com.example.ws_calendarlayout.calendar.common.calculateDateDifference
import com.example.ws_calendarlayout.calendar.common.equalCalendar
import com.example.ws_calendarlayout.calendar.common.getCalendarFormatter
import com.example.ws_calendarlayout.calendar.screen.item.common.CalendarItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor()
    : OrbitViewModel<CalendarState, CalendarSideEffect>(CalendarState) {

    // first : checkIn
    // second : checkOut
    private var checkInOut : Pair<Calendar?, Calendar?> = Pair<Calendar?, Calendar?>(null, null)
    private val clearScreenList : ArrayList<Int?> = ArrayList<Int?>()

    fun setCheckInOut(checkInOut: Pair<Calendar?, Calendar?>){
        setCheckInOutInCalendar(checkInOut.first)
        setCheckInOutInCalendar(checkInOut.second)
    }

    fun setCheckInOutInCalendar(calendar: Calendar?) {
        if (checkInOut.first == null) checkInOut = Pair(calendar, checkInOut.second)
        else if (checkInOut.second == null){ if (!calendar.equalCalendar(checkInOut.first)) checkInOut = Pair(checkInOut.first, calendar) }
        else {
            checkInOut = Pair(calendar, null)
            callClearScreen()
        }

        val checkIn = checkInOut.first
        val checkOut = checkInOut.second

        if (checkIn != null && checkOut != null) {
            // 체크인이 체크아웃보다 늦는 시기.
            if (checkIn > checkOut) checkInOut = Pair(checkOut, checkIn)
            callRefreshScreen()
        }
        showCompleteText()
    }

    fun makeCalendarItemState(calendar :Calendar?) : CalendarItemState {
        var calendarItemState = CalendarItemState.NONE
        calendar?.let {
            val today = Calendar.getInstance()
            val isWeekend = ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))

            if (calendar.equalCalendar(today)) calendarItemState = CalendarItemState.TODAY // 오늘
            else if (calendar < today) calendarItemState = if (isWeekend) CalendarItemState.BEFORE_TODAY_WEEKEND else CalendarItemState.BEFORE_TODAY // 오늘 이전
            else if (isWeekend) calendarItemState = CalendarItemState.WEEKEND // 주말

            // 체크인 & 체크아웃
            if (calendar.equalCalendar(checkInOut.first)) calendarItemState = CalendarItemState.CHECK_IN
            else if (calendar.equalCalendar(checkInOut.second)) calendarItemState = CalendarItemState.CHECK_OUT

            val checkIn = checkInOut.first
            val checkOut = checkInOut.second
            if (checkIn != null && checkOut != null) {
                if ((checkIn < calendar) && (checkOut > calendar) && (calendarItemState != CalendarItemState.CHECK_IN) && (calendarItemState != CalendarItemState.CHECK_OUT)){
                    calendarItemState = if (isWeekend) CalendarItemState.RANGE_WEEKEND else CalendarItemState.RANGE
                }
            }
            return calendarItemState
        } ?: run {
            return  calendarItemState
        }
    }

    fun getCheckInOut() : Pair<Calendar?, Calendar?> = checkInOut

    private fun callRefreshScreen(){ postAction(CalendarSideEffect.RefreshScreen) }

    fun addClearScreenList(index: Int){ clearScreenList.add(index) }
    fun resetClearScreenList(){ clearScreenList.clear() }

    private fun callClearScreen() { postAction(CalendarSideEffect.ClearScreen(indexList = clearScreenList)) }

    fun showCompleteText(){
        val checkIn = checkInOut.first
        val checkOut = checkInOut.second

        val checkInStr = checkIn?.let { getCalendarFormatter(calendar = it, format = "M.d(E)") } ?: ""
        val checkOutStr = checkOut?.let { getCalendarFormatter(calendar = (it), format = "M.d(E)") } ?: ""

        val diffDay = if (checkIn != null && checkOut != null) calculateDateDifference(checkIn, checkOut).toInt() else 0
        val diffDayStr = if (diffDay > 0) "·${diffDay}박" else ""
        val result = "$checkInStr - $checkOutStr $diffDayStr / 선택완료"
        postAction(CalendarSideEffect.ShowBottomText(result))
    }

    fun getCheckInOutDate() :Pair<Date, Date>?{
        val checkIn = checkInOut.first
        val checkOut = checkInOut.second
        return if (checkIn != null && checkOut != null) {
            val checkInDate = checkIn.time
            val checkOutDate = checkOut.time
            Pair<Date, Date>(checkInDate, checkOutDate)
        } else null
    }

    fun isExistCheckInOut() : Boolean = (checkInOut.first != null && checkInOut.second != null)

}