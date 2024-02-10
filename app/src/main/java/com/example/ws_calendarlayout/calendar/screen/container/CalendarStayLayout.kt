package com.example.ws_calendarlayout.calendar.screen.container

import android.app.Activity
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.example.ws_calendarlayout.calendar.common.CalendarSideEffect
import com.example.ws_calendarlayout.calendar.common.CalendarState
import com.example.ws_calendarlayout.calendar.common.Define
import com.example.ws_calendarlayout.calendar.common.Define.Companion.MAX_MONTH_CALENDAR
import com.example.ws_calendarlayout.calendar.common.ResourceData
import com.example.ws_calendarlayout.calendar.screen.adapter.ViewPagerAdapter
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.ActivityStayCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class CalendarStayLayout(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
    private val checkIn: Date,
    private val checkOut: Date,
    private val orientation: Define.ORIENTATION = Define.ORIENTATION.VERTICAL,
    private val resourceData: ResourceData
) : FrameLayout(activity) {

    @Inject
    lateinit var calendarViewModel: CalendarViewModel
    private val binding: ActivityStayCalendarBinding = ActivityStayCalendarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setInitData()
        setViewPager()
        setCalendarList()
        moveCalendar()
        calendarViewModel.observe(lifecycleOwner = lifecycleOwner, state = ::render, sideEffect = ::handleSideEffect)
    }
    private fun render(state: CalendarState){ }

    private fun handleSideEffect(sideEffect: CalendarSideEffect) {
        when(sideEffect){
            is CalendarSideEffect.ClearScreen -> {
                (binding.recyclerView.adapter as? ViewPagerAdapter)?.let { recyclerView ->
                    activity.runOnUiThread {
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
                        activity.runOnUiThread {
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
                resourceData = resourceData
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

}