package com.example.ws_calendarlayout.calendar.screen.viewHolder

import android.graphics.Typeface
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import com.example.ws_calendarlayout.base.BaseViewHolder
import com.example.ws_calendarlayout.calendar.common.CalendarData
import com.example.ws_calendarlayout.calendar.common.getCalendarFormatter
import com.example.ws_calendarlayout.calendar.common.getFontInAssets
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.CalendarActivityViewPagerLayoutBinding
import com.example.ws_calendarlayout.utils.dpToPixel
import com.example.ws_calendarlayout.utils.setTextColorResource
import java.util.Calendar

class ViewPagerViewHolder(
    private val binding: CalendarActivityViewPagerLayoutBinding,
    private val calendarViewModel: CalendarViewModel,
    private val calendarData: CalendarData
) : BaseViewHolder(binding.root) {

    init {
        binding.calendarLayout.setRecyclerView(
            calendarViewModel = calendarViewModel,
            calendarData = calendarData
        )
    }

    fun onBind(calendar: Calendar, isLast: Boolean) {
        val calendarTitle = getCalendarFormatter(calendar = calendar, format = "yyyy년 M월")
        binding.calendarTitle.apply {
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, calendarData.titleTextResource.size.toFloat())
            setTextColorResource(calendarData.titleTextResource.color)
            calendarData.titleTextResource.font?.let { typeface = context.getFontInAssets(it) }
            text = calendarTitle
        }
        binding.calendarLayout.apply {
            setData(calendar)
        }
        (binding.rootLayout.layoutParams as? RecyclerView.LayoutParams)?.setMargins(0,25.dpToPixel(),0,if (isLast) 4.dpToPixel() else 0)
        binding.executePendingBindings()
    }
}