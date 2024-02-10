package com.example.ws_calendarlayout.calendar.screen.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.ws_calendarlayout.base.BaseViewHolder
import com.example.ws_calendarlayout.calendar.common.getCalendarFormatter
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.CalendarActivityViewPagerLayoutBinding
import com.example.ws_calendarlayout.utils.dpToPixel
import java.util.Calendar

class ViewPagerViewHolder(
    private val binding: CalendarActivityViewPagerLayoutBinding,
    private val calendarViewModel: CalendarViewModel
) : BaseViewHolder(binding.root) {

    init {
        binding.calendarLayout.setRecyclerView(calendarViewModel)
    }

    fun onBind(calendar: Calendar, isLast: Boolean) {
        val calendarTitle = getCalendarFormatter(calendar = calendar, format = "yyyy년 M월")
        binding.calendarTitle.text = calendarTitle
        binding.calendarLayout.setData(calendar)
        (binding.rootLayout.layoutParams as? RecyclerView.LayoutParams)?.setMargins(0,25.dpToPixel(),0,if (isLast) 4.dpToPixel() else 0)
        binding.executePendingBindings()
    }
}