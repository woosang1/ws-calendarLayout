package com.example.ws_calendarlayout.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.ws_calendarlayout.calendar.viewModel.CalendarViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {
    @Provides
    fun provideCalendarViewModel(): CalendarViewModel {
        return ViewModelProvider(
            ViewModelStoreOwner { ViewModelStore() },
        ).get(CalendarViewModel::class.java)
    }
}