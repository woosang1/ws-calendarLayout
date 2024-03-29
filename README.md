<h1 align="center">ws-calendar</h1></br>
<p align="center">
🎨 "ws-calendar" is a calendar layout where you can select both the start and end dates. It is commonly used for features like selecting check-in and check-out dates, offering versatility in both horizontal and vertical orientations. Users can customize the layout by selecting various elements to create the desired UI.
</p>
<br>

<p align="center">
  <a href="https://github.com/woosang1/ws-calendarLayout"><img alt="Google" src="https://img.shields.io/badge/woosang-%20?style=flat&logo=android&logoColor=34A853&label=Google%20DevLibrary&labelColor=ffffff&color=FFA500"/></a><br>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=15"><img alt="API" src="https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/skydoves/ColorPickerView/actions"><img alt="Build Status" src="https://github.com/skydoves/ColorPickerView/workflows/Android%20CI/badge.svg"/></a>
  <a href="https://androidweekly.net/issues/issue-316"><img alt="Android Weekly" src="https://skydoves.github.io/badges/android-weekly.svg"/></a>
</p> <br>

<p align="center">
<img src="/art/art0.gif" width="31%"/>
<img src="/art/art1.gif" width="31%"/>
</p>


## Preview
<img src="https://github.com/woosang1/ws-calendarLayout/assets/45825518/bd61fceb-e677-48c2-b0cf-f4a7638fb95e" alt="preview" width="500" height="1000">
<img src="https://github.com/woosang1/ws-calendarLayout/assets/45825518/02b5d466-fa53-4020-a935-adb6ac3ea410" alt="preview" width="500" height="1000">


## Including in your project 
[![Maven Central](https://img.shields.io/maven-central/v/com.github.skydoves/colorpickerview.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.skydoves%22%20AND%20a:%22colorpickerview%22)

### Gradle 

Add the dependency below to your module's `build.gradle` file:

```gradle
dependencies {
    implementation "com.github.woosang1:ws_calendarLayout:1.0.0"
}
```

## How to Use
Calnedar support Kotlin, so you can reference it by your language.

### Create Calendar with Kotlin
We can create an instance of the Calnedar with the `CalendarStayLayout` class.
#### xml
```xml
<com.example.ws_calendarlayout.calendar.screen.container.CalendarStayLayout
            android:id="@+id/calendarLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

</com.example.ws_calendarlayout.calendar.screen.container.CalendarStayLayout>
```

#### .kt
```kotlin
binding.calendarLayout.apply {
    setLifecycleOwner(this@MainActivity)
    setMaxMonthCalendar(maxMonthCalendar = 4)
    setOrientation(orientation = ORIENTATION.HORIZONTAL)
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
    setDescriptionFont(
        descriptionResource = TextResource(
            size = 10,
            color = R.color.gray_3,
            font = R.font.pretendard_regular
        )
    )
    create()
}
```

### Orientation
Horizontal or Vertical<br>
```kotlin
setOrientation(orientation = ORIENTATION.HORIZONTAL) ## Horizontal
setOrientation(orientation = ORIENTATION.HORIZONTAL) ## Vertical
```
<br>
#### Horizontal <br>
<img src="https://github.com/woosang1/ws-calendarLayout/assets/45825518/af13a09a-3f46-4c85-b203-247240f0f4ff" alt="preview" width="500" height="1000">
<br>

#### Vertical <br>
<img src="https://github.com/woosang1/ws-calendarLayout/assets/45825518/555c456a-2814-4bc0-a5e1-219552003876" alt="preview" width="500" height="1000">
<br>


### MaxMonth
How many months will you expose? Default: 6<br>
```kotlin
setMaxMonthCalendar(maxMonthCalendar = 4)
```

### CheckIn & CheckOut
At the start of the Calendar, specify the date of check-in and check-out.<br>
```kotlin
setCheckIn(checkIn = Date()) ## today
setCheckOut(checkOut = Calendar.getInstance().apply { add(Calendar.DATE, 2) }.time) ## today+2
```

### Title
Sets the font for calendar information that is exposed to the kelinder. (yyyy년 M월)<br>
```kotlin
setTextResourceByTitle(
    titleTextResource = TextResource(
        size = 14,
        color = R.color.black,
        font = null
    )
)
```

### DaysOfTheWeek
Set the font for the day of the week you are exposed to at the top of the calendar (Sun/Monday/Tuesday/Wed/Thursday/Fri/Saturday)<br>
```kotlin
setTextResourceByDaysOfTheWeek(
    daysOfTheWeekTextResource = TextResource(
        size = 10,
        color = R.color.gray_2,
        font = null
    )
)
```

### Day
Sets the font for the day of exposure to the calendar.<br>
```kotlin
setTextResourceByDay(
    daysTextResource = TextResource(
        size = 10,
        color = R.color.gray_3,
        font = null
    )
)
```

### Description
You can change the font of the description text.
(Color cannot be changed. / It can be changed to textColorRes in CalendarItemState.)<br>
```kotlin
setDescriptionFont(
    descriptionResource = TextResource(
        size = 10,
        color = R.color.gray_3,
        font = R.font.pretendard_regular
    )
)
```

### CalendarItemState
Set font and background according to the status that appears in the kelinder.<br>
```kotlin
setCalendarItemState(
    mCalendarItemState = CalendarItemState.TODAY.apply {
        text = "Today"
        textColorRes = R.color.blue
        bgRes = null
        alpha = 1.0f
        isClickable = true
    }
)
```



# License
```xml
Copyright 2024 woosang1 (woosang Lee)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
