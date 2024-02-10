package com.example.ws_calendarlayout.calendar.screen.item.viewHolder

import android.view.View
import com.example.ws_calendarlayout.base.BaseViewHolder
import com.example.ws_calendarlayout.databinding.CalendarActivityItemLayoutBinding
import com.example.ws_calendarlayout.calendar.screen.item.common.CalendarClickInterface
import com.example.ws_calendarlayout.calendar.screen.item.common.CalendarItemState
import java.util.Calendar

class CalendarItemViewHolder(
    val binding: CalendarActivityItemLayoutBinding,
    val dayTitle: List<String>,
) : BaseViewHolder(binding.root) {

    private lateinit var calendarClickInterface: CalendarClickInterface
    private var calendarItem: Calendar? = null

    fun onBind(value: String, calendarItem: Calendar?, calendarItemState: CalendarItemState) {
        this.calendarItem = calendarItem
        setView(value = value, calendarItemState = calendarItemState)
        binding.executePendingBindings()
    }

    private fun setView(value: String, calendarItemState: CalendarItemState) {
        binding.rootLayout.setBackgroundResource(0)
        binding.rootLayout.setOnClickListener {  }

        // 날짜 텍스트
        binding.calendarItemText.text = value
        binding.calendarItemText.alpha = calendarItemState.alpha
        val isDay = !dayTitle.contains(value)

        // 요일
        if (!isDay){
//            setFontStyle(binding.calendarItemText, FontStyle.LABEL4.type)
//            binding.calendarItemText.setTextColorResource(R.color.gray_3)
            binding.descriptionTextView.visibility = View.GONE
        }
        // 일
        else{
//            setFontStyle(binding.calendarItemText, FontStyle.LABEL3.type)
//            binding.calendarItemText.setTextColorResource(calendarItemState.textColorRes)
            binding.rootLayout.setBackgroundResource(calendarItemState.bgRes ?: 0)

            // 설명 텍스트
            binding.descriptionTextView.apply {
//                setTextColorResource(calendarItemState.textColorRes)
                text = calendarItemState.text
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
