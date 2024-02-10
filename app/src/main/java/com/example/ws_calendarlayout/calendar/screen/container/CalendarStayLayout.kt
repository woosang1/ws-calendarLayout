package com.example.ws_calendarlayout.calendar.screen.container

import android.app.Activity
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ws_calendarlayout.calendar.common.CalendarSideEffect
import com.example.ws_calendarlayout.calendar.common.CalendarState
import com.example.ws_calendarlayout.calendar.common.ClosePopupInterface
import com.example.ws_calendarlayout.calendar.common.Define.Companion.MAX_MONTH_CALENDAR
import com.example.ws_calendarlayout.calendar.screen.adapter.ViewPagerAdapter
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.ActivityStayCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class CalendarStayLayout constructor(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
    private val checkIn: Date?,
    private val checkOut: Date?,
    private val closePopupInterface: ClosePopupInterface?,
    private val dismissCallBack: () -> Unit,
) : FrameLayout(activity) {

    @Inject
    lateinit var calendarViewModel: CalendarViewModel
    private var isClickableByCompleteBtn : Boolean = false
    private val binding: ActivityStayCalendarBinding = ActivityStayCalendarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setInitData()
        setViewPager()
        setCalendarList()
        setClickListener()
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

            is CalendarSideEffect.ShowBottomText -> {
                binding.completeBtnText.apply {
                    text = sideEffect.title
                    if (calendarViewModel.isExistCheckInOut()){
                        alpha = 1f
                        isClickableByCompleteBtn = true
                    }
                    else {
                        alpha = 0.3f
                        isClickableByCompleteBtn = false
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
        val checkIn = if (checkIn != null) Calendar.getInstance().apply { time = checkIn } else null
        val checkOut = if (checkOut != null) Calendar.getInstance().apply { time = checkOut } else null
        calendarViewModel.setCheckInOut(Pair(checkIn, checkOut))
        calendarViewModel.showCompleteText()
    }

    private fun setViewPager() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ViewPagerAdapter(calendarViewModel)
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

    private fun setClickListener(){
        binding.closeBtn.setOnClickListener { dismissCallBack.invoke() }
        binding.completeBtn.setOnClickListener {
            if (isClickableByCompleteBtn) {
                it.setOnClickListener {
//                    closePopupInterface?.close(calendarViewModel.getCheckInOutDate())
                    dismissCallBack.invoke()
                }
            }
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