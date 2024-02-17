package com.example.ws_calendarlayout.calendar.screen.item.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ws_calendarlayout.base.BaseRecyclerAdapter
import com.example.ws_calendarlayout.base.BaseViewHolder
import com.example.ws_calendarlayout.calendar.common.CalendarData
import com.example.ws_calendarlayout.calendar.common.CalendarItemState
import com.example.ws_calendarlayout.calendar.screen.item.common.CalendarClickInterface
import com.example.ws_calendarlayout.calendar.screen.item.viewHolder.CalendarItemViewHolder
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.CalendarActivityItemLayoutBinding
import java.util.Calendar

class CalendarAdapter(
    private val calendarViewModel: CalendarViewModel,
    private val daysOfWeekTitle: List<String>,
    private val calendarData: CalendarData
) : BaseRecyclerAdapter<String>() {

    private var calendarDate: Calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CalendarItemViewHolder(
            binding = CalendarActivityItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            dayTitle = daysOfWeekTitle,
            calendarData = calendarData
        )
    }

    override fun onBindViewHolder(defaultViewHolder: RecyclerView.ViewHolder, position: Int) {
        val dayStr = model[position]
        var itemCalendar: Calendar? = null

        val isDay = dayStr.isNotEmpty() && !getIsTitleDay(dayStr)

        if (isDay) {
            itemCalendar = Calendar.getInstance()
            itemCalendar?.let {
                it.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR))
                it.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH))
                it.set(Calendar.DATE, dayStr.toInt())
            }
        }

        (defaultViewHolder as? CalendarItemViewHolder)?.run {
            onBind(
                value = dayStr,
                calendarItem = itemCalendar,
                calendarItemState =
                if (isDay) calendarViewModel.makeCalendarItemState(itemCalendar)
                else CalendarItemState.NONE
            )

            setClickInterface(
                calendarClickInterface = object : CalendarClickInterface {
                    override fun clickDate() {
                        val calendarItem = defaultViewHolder.getCalendarItem()
                        calendarViewModel.setCheckInOutInCalendar(calendar = calendarItem)
                        notifyDataSetChanged()
                    }
                }
            )
        }
    }

    fun setCalendarDate(calendarDate: Calendar) {
        this.calendarDate = calendarDate
    }

    private fun getIsTitleDay(dayStr: String): Boolean {
        val isTitleDay = daysOfWeekTitle.filter { it == dayStr }
        return isTitleDay.isNotEmpty()
    }

    override fun getItemId(position: Int): Long = position.toLong()

}



