package com.example.ws_calendarlayout

import android.view.LayoutInflater
import com.example.ws_calendarlayout.base.BaseActivity
import com.example.ws_calendarlayout.calendar.common.ResourceData
import com.example.ws_calendarlayout.calendar.screen.container.CalendarStayLayout
import com.example.ws_calendarlayout.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initBinding(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onInitBinding() {
        binding.rootLayout.addView(
            CalendarStayLayout(
                activity = this@MainActivity,
                lifecycleOwner = this@MainActivity,
                checkIn = Date(),
                checkOut = Calendar.getInstance().apply { add(Calendar.DATE, 2) }.time,
                resourceData = ResourceData(
                    title = ResourceData.Text(
                        size = 14,
                        color = R.color.black,
                        font = null
                    ),
                    daysOfTheWeek = ResourceData.Text(
                        size = 10,
                        color = R.color.gray_2,
                        font = null
                    ),
                    day = ResourceData.Text(
                        size = 10,
                        color = R.color.gray_3,
                        font = null
                    ),
                    descriptionFont = null
                )
            )
        )
    }
}