package com.prbansal.dayscheduler.home


import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.prbansal.dayscheduler.convertDurationToFormatted
import com.prbansal.dayscheduler.database.DayTask


/*@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: DayTask?) {
    item?.let {
        setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
}*/


@BindingAdapter("taskDesc")
fun TextView.setSleepQualityString(item: DayTask?) {
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }
}