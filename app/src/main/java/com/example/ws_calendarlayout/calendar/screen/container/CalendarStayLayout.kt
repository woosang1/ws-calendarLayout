package com.example.ws_calendarlayout.calendar.screen.container

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.example.ws_calendarlayout.calendar.common.CalendarData
import com.example.ws_calendarlayout.calendar.common.CalendarSideEffect
import com.example.ws_calendarlayout.calendar.common.CalendarState
import com.example.ws_calendarlayout.calendar.common.Define
import com.example.ws_calendarlayout.calendar.common.Define.Companion.MAX_MONTH_CALENDAR
import com.example.ws_calendarlayout.calendar.common.TextResource
import com.example.ws_calendarlayout.calendar.screen.adapter.ViewPagerAdapter
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.ActivityStayCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.observe
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class CalendarStayLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ActivityStayCalendarBinding = ActivityStayCalendarBinding.inflate(LayoutInflater.from(context), this, true)
    private var lifecycleOwner: LifecycleOwner? = null
    private var orientation: Define.ORIENTATION = Define.ORIENTATION.VERTICAL
    private var checkIn: Date = Date()
    private var checkOut: Date = Date()
    private var titleTextResource = TextResource()
    private var daysOfTheWeekTextResource = TextResource()
    private var daysTextResource = TextResource()

    @Inject
    lateinit var calendarViewModel: CalendarViewModel

    private fun render(state: CalendarState){ }

    private fun handleSideEffect(sideEffect: CalendarSideEffect) {
        when(sideEffect){
            is CalendarSideEffect.ClearScreen -> {
                (binding.recyclerView.adapter as? ViewPagerAdapter)?.let { recyclerView ->
                    MainScope().launch {
                        sideEffect.indexList.forEach { it?.let { index -> recyclerView.notifyItemChanged(index) } }
                        calendarViewModel.resetClearScreenList()
                    }
                }
            }

            is CalendarSideEffect.RefreshScreen -> {
                var startIndex: Int? = null
                var endIndex: Int? = null
                val checkInOut = calendarViewModel.getCheckInOut()

                val yaerCheckIn = checkInOut.first?.get(Calendar.YEAR)
                val monthCheckIn = checkInOut.first?.get(Calendar.MONTH)

                val yaerCheckOut = checkInOut.second?.get(Calendar.YEAR)
                val monthCheckOut = checkInOut.second?.get(Calendar.MONTH)

                if (yaerCheckIn != null && monthCheckIn != null) startIndex = findIndexEqualCalendar(year = yaerCheckIn, month = monthCheckIn)
                if (yaerCheckOut != null && monthCheckOut != null) endIndex = findIndexEqualCalendar(year = yaerCheckOut, month = monthCheckOut)

                if (startIndex != null && endIndex != null) {
                    if (startIndex <= endIndex) {
                        MainScope().launch {
                            for (i in startIndex until endIndex + 1) {
                                calendarViewModel.addClearScreenList(i)
                                (binding.recyclerView.adapter as? ViewPagerAdapter)?.notifyItemChanged(i)
                            }
                        }
                    }
                }
            }
        }
    }

    fun create(){
        setInitData()
        setViewPager()
        setCalendarList()
        moveCalendar()
        lifecycleOwner?.let { calendarViewModel.observe(lifecycleOwner = it, state = ::render, sideEffect = ::handleSideEffect) }
    }

    private fun setInitData(){
        val checkIn = Calendar.getInstance().apply { time = checkIn }
        val checkOut = Calendar.getInstance().apply { time = checkOut }
        calendarViewModel.setCheckInOut(Pair(checkIn, checkOut))
        calendarViewModel.showCompleteText()
    }

    private fun setViewPager() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context,
                if (orientation == Define.ORIENTATION.HORIZONTAL) LinearLayoutManager.HORIZONTAL
                else LinearLayoutManager.VERTICAL, false)
            adapter = ViewPagerAdapter(
                calendarViewModel = calendarViewModel,
                calendarData = CalendarData(
                    checkIn = checkIn,
                    checkOut = checkOut,
                    titleTextResource = titleTextResource,
                    daysOfTheWeekTextResource = daysOfTheWeekTextResource,
                    daysTextResource = daysTextResource
                )
            )
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setCalendarList() {
        (binding.recyclerView.adapter as? ViewPagerAdapter)?.run {
            model.clear()
            addData(
                ArrayList<Calendar>().apply {
                    // 최대 6개 -> 오늘
                    val today = Calendar.getInstance()
                    add(today)
                    for (i in 1 until MAX_MONTH_CALENDAR) {
                        val todayCopy = today.clone() as Calendar
                        todayCopy.add(Calendar.MONTH, i)
                        add(todayCopy)
                    }
                }
            )
        }
    }

    private fun findIndexEqualCalendar(year: Int, month: Int): Int? {
        (binding.recyclerView.adapter as? ViewPagerAdapter)?.let {
            it.model.forEachIndexed { index, calendar ->
                val yearInList = calendar.get(Calendar.YEAR)
                val monthInList = calendar.get(Calendar.MONTH)
                if (year == yearInList && month == monthInList) return index
            }
            return null
        } ?: run {
            return null
        }
    }

    private fun moveCalendar(){
        val checkIn = calendarViewModel.getCheckInOut().first
        if (checkIn != null){
            val year = checkIn.get(Calendar.YEAR)
            val month = checkIn.get(Calendar.MONTH)
            val index = findIndexEqualCalendar(year = year, month = month)
            binding.recyclerView.scrollToPosition(index ?: 0)
        }
        else binding.recyclerView.scrollToPosition(0)
    }

    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner){ this.lifecycleOwner = lifecycleOwner }

    fun setOrientation(orientation: Define.ORIENTATION){ this.orientation = orientation }
    fun setCheckIn(checkIn: Date){ this.checkIn = checkIn }
    fun setCheckOut(checkOut: Date){ this.checkOut = checkOut }
    fun setTextResourceByTitle(titleTextResource : TextResource){ this.titleTextResource = titleTextResource }
    fun setTextResourceByDaysOfTheWeek(daysOfTheWeekTextResource : TextResource){ this.daysOfTheWeekTextResource = daysOfTheWeekTextResource }
    fun setTextResourceByDay(daysTextResource : TextResource){ this.daysTextResource = daysTextResource }


}