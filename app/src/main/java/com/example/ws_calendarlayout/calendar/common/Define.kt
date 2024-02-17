package com.example.ws_calendarlayout.calendar.common

import com.example.ws_calendarlayout.R

const val MAX_MONTH_CALENDAR = 6
enum class ORIENTATION {
    HORIZONTAL,
    VERTICAL
}

enum class CalendarItemState(
    var text: String,
    var textColorRes: Int,
    var bgRes: Int?,
    var alpha: Float,
    var isClickable: Boolean
) {
    CHECK_IN(text = "체크인", textColorRes = R.color.tint_secondary, bgRes = R.drawable.primary_left_radius_4, alpha = 1.0f, isClickable = true),
    CHECK_OUT(text = "체크아웃", textColorRes = R.color.tint_secondary, bgRes = R.drawable.primary_right_radius_4, alpha = 1.0f, isClickable = true),
    TODAY(text = "오늘", textColorRes = R.color.B01, bgRes = null, alpha = 1.0f, isClickable = true),
    BEFORE_TODAY(text = "", textColorRes = R.color.gray_2, bgRes = null, alpha = 0.2f, isClickable = false),
    BEFORE_TODAY_WEEKEND(text = "", textColorRes = R.color.primary, bgRes = null, alpha = 0.2f, isClickable = false),
    WEEKEND(text = "", textColorRes = R.color.primary, bgRes = null, alpha = 1.0f, isClickable = true),
    RANGE(text = "", textColorRes = R.color.gray_2, bgRes = R.color.primary_alpha10, alpha = 1.0f, isClickable = true),
    RANGE_WEEKEND(text = "", textColorRes = R.color.primary, bgRes = R.color.primary_alpha10, alpha = 1.0f, isClickable = true),
    NONE(text = "", textColorRes = R.color.gray_2, bgRes = null, alpha = 1.0f, isClickable = true);
}
