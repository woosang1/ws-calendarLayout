package com.example.ws_calendarlayout.calendar.screen.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ws_calendarlayout.R
import com.example.ws_calendarlayout.calendar.screen.item.adapter.CalendarAdapter
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.CalendarActivityLayoutBinding
import com.example.ws_calendarlayout.utils.ItemGridDecorator
import com.example.ws_calendarlayout.utils.dpToPixel
import java.util.Calendar

class CalendarLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: CalendarActivityLayoutBinding = CalendarActivityLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private var daysOfWeekTitle = resources.getStringArray(R.array.days_of_week).toList()

    fun setRecyclerView(calendarViewModel: CalendarViewModel) {
        with(binding) {
            calendarRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 7)
                itemAnimator = DefaultItemAnimator()
                adapter = CalendarAdapter(
                    calendarViewModel = calendarViewModel,
                    daysOfWeekTitle = daysOfWeekTitle
                ).apply {
                    setHasStableIds(true)
                }
                while (itemDecorationCount > 0) { removeItemDecorationAt(0) }
                addItemDecoration(
                    ItemGridDecorator(
                        spanCount = 7,
                        topMargin = 0.dpToPixel(),
                        bottomMargin = 0.dpToPixel(),
                        startMargin = 0.dpToPixel(),
                        endMargin = 0.dpToPixel(),
                        middleVerticalMargin = 24.dpToPixel(),
                        middleHorizontalMargin = 0.dpToPixel()
                    )
                )
            }
        }
    }

    fun setData(calendarDate: Calendar) {
        val tmpDate = calendarDate.clone() as Calendar
        tmpDate.set(Calendar.DAY_OF_MONTH, 1)
        // 이번 달 첫 요일 (일-1 / 월-2 / 화-3)
        val week = tmpDate.get(Calendar.DAY_OF_WEEK)
        // 이번 달 마지막 날짜(일) (29 / 30 / 31)
        val dayOfMonth = tmpDate.getActualMaximum(Calendar.DAY_OF_MONTH)
        val modelList = ArrayList<String>()
        modelList.addAll(daysOfWeekTitle)
        val maxSize = dayOfMonth + week - 1
        for (i in 0..maxSize) {
            // 요일 빈 공백
            if (i < week - 1) modelList.add("")
            else {
                val inputDate = (i - (week - 2))
                // 날짜 추가
                if (inputDate <= dayOfMonth) modelList.add(inputDate.toString())
            }
        }
        (binding.calendarRecyclerView.adapter as? CalendarAdapter)?.apply {
            setCalendarDate(calendarDate)
            model.clear()
            model.addAll(modelList)
            notifyItemRangeChanged(0, modelList.size)
        }
    }


}

