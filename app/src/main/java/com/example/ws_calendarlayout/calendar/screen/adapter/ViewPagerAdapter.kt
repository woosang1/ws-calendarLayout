package com.example.ws_calendarlayout.calendar.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ws_calendarlayout.base.BaseRecyclerAdapter
import com.example.ws_calendarlayout.base.BaseViewHolder
import com.example.ws_calendarlayout.calendar.common.ResourceData
import com.example.ws_calendarlayout.calendar.screen.viewHolder.ViewPagerViewHolder
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import com.example.ws_calendarlayout.databinding.CalendarActivityViewPagerLayoutBinding
import java.util.Calendar

class ViewPagerAdapter(
    private val calendarViewModel: CalendarViewModel,
    private val resourceData: ResourceData
) : BaseRecyclerAdapter<Calendar>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewPagerViewHolder(
            binding = CalendarActivityViewPagerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            calendarViewModel = calendarViewModel,
            resourceData = resourceData
        )
    }

    override fun onBindViewHolder(defaultViewHolder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(defaultViewHolder, position)
        (defaultViewHolder as? ViewPagerViewHolder)?.onBind(calendar = model[position], isLast = (position == (model.size-1)))
    }

}