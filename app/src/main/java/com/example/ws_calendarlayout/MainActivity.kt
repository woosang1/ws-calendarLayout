package com.example.ws_calendarlayout

import android.view.LayoutInflater
import com.example.ws_calendarlayout.base.BaseActivity
import com.example.ws_calendarlayout.calendar.common.Define
import com.example.ws_calendarlayout.calendar.common.TextResource
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
                lifecycleOwner = this@MainActivity
            ).apply {
                setOrientation(orientation = Define.ORIENTATION.VERTICAL)
                setCheckIn(checkIn = Date())
                setCheckOut(checkOut = Calendar.getInstance().apply { add(Calendar.DATE, 2) }.time)
                setTextResourceByTitle(
                    titleTextResource = TextResource(
                        size = 14,
                        color = R.color.black,
                        font = null
                    )
                )
                setTextResourceByDaysOfTheWeek(
                    daysOfTheWeekTextResource = TextResource(
                        size = 10,
                        color = R.color.gray_2,
                        font = null
                    )
                )
                setTextResourceByDay(
                    daysTextResource = TextResource(
                        size = 10,
                        color = R.color.gray_3,
                        font = null
                    )
                )
                create()
            }
        )
    }
}