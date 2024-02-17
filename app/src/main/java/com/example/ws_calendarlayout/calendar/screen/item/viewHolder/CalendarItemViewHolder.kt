package com.example.ws_calendarlayout.calendar.screen.item.viewHolder

import android.util.TypedValue
import android.view.View
import com.example.ws_calendarlayout.base.BaseViewHolder
import com.example.ws_calendarlayout.calendar.common.CalendarData
import com.example.ws_calendarlayout.calendar.common.CalendarItemState
import com.example.ws_calendarlayout.calendar.common.getFontInAssets
import com.example.ws_calendarlayout.databinding.CalendarActivityItemLayoutBinding
import com.example.ws_calendarlayout.calendar.screen.item.common.CalendarClickInterface
import com.example.ws_calendarlayout.utils.setTextColorResource
import java.util.Calendar

class CalendarItemViewHolder(
    private val binding: CalendarActivityItemLayoutBinding,
    private val dayTitle: List<String>,
    private val calendarData: CalendarData
) : BaseViewHolder(binding.root) {

    private lateinit var calendarClickInterface: CalendarClickInterface
    private var calendarItem: Calendar? = null

    fun onBind(value: String, calendarItem: Calendar?, calendarItemState: CalendarItemState) {
        this.calendarItem = calendarItem
        setView(value = value, calendarItemState = calendarItemState, calendarData = calendarData)
        binding.executePendingBindings()
    }

    private fun setView(value: String, calendarItemState: CalendarItemState, calendarData: CalendarData) {
        binding.rootLayout.apply {
            setBackgroundResource(0)
            setOnClickListener {  }
        }

        // 날짜 텍스트
        binding.calendarItemText.apply {
            text = value
            alpha = calendarItemState.alpha
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, calendarData.daysTextResource.size.toFloat())
            setTextColorResource(calendarData.daysTextResource.color)
            calendarData.daysTextResource.font?.let { typeface = context.getFontInAssets(it) }
        }

        val isDay = !dayTitle.contains(value)

        // 요일
        if (!isDay){
            binding.calendarItemText.apply {
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, calendarData.daysOfTheWeekTextResource.size.toFloat())
                setTextColorResource(calendarData.daysOfTheWeekTextResource.color)
                calendarData.daysOfTheWeekTextResource.font?.let { typeface = context.getFontInAssets(it) }
            }
            binding.descriptionTextView.visibility = View.GONE
        }
        // 일
        else{
            binding.calendarItemText.setTextColorResource(calendarItemState.textColorRes)
            binding.rootLayout.setBackgroundResource(calendarItemState.bgRes ?: 0)

            // 설명 텍스트
            binding.descriptionTextView.apply {
                setTextColorResource(calendarItemState.textColorRes)
                text = calendarItemState.text
                setTextSize(TypedValue.COMPLEX_UNIT_DIP, calendarData.descriptionResource.size.toFloat())
                calendarData.descriptionResource.font?.let { typeface = context.getFontInAssets(it) }
                visibility = if (calendarItemState.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
            }

            // 활성화
            if (calendarItemState.isClickable) binding.rootLayout.setOnClickListener { calendarClickInterface.clickDate() }
        }
    }

    fun getCalendarItem() : Calendar? = calendarItem

    fun setClickInterface(calendarClickInterface: CalendarClickInterface) {
        this.calendarClickInterface = calendarClickInterface
    }
}
