package com.example.ws_calendarlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.ws_calendarlayout.base.BaseActivity
import com.example.ws_calendarlayout.calendar.screen.container.CalendarStayLayout
import com.example.ws_calendarlayout.databinding.ActivityMainBinding
import com.example.ws_calendarlayout.databinding.ActivityStayCalendarBinding
import dagger.hilt.android.AndroidEntryPoint
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
                checkOut = Date(),
                closePopupInterface = null,
                dismissCallBack = {}
            ))
    }
}