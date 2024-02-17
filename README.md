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


## preview






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

### Create Balloon with Kotlin
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
    create()
}
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
