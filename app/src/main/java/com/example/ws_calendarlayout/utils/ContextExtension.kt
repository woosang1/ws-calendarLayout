package com.example.ws_calendarlayout.utils

import android.content.Context
import android.content.res.Resources
import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.setTextColorResource(resource: Int) =
    this.setTextColor(context.getColorCompat(resource))

fun Context.getColorCompat(colorId: Int): Int = ContextCompat.getColor(this, colorId)
