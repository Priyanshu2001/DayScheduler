package com.prbansal.dayscheduler

import android.content.res.Resources
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
    val durationMilli = endTimeMilli - startTimeMilli

    fun calculateDurStr() :String {
        return when {
            durationMilli < ONE_MINUTE_MILLIS -> {
                val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
                res.getString(R.string.seconds_length, seconds)
            }
            durationMilli < ONE_HOUR_MILLIS -> {
                val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
                res.getString(R.string.minutes_length, minutes)
            }
            else -> {
                val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
                res.getString(R.string.hours_length, hours)
            }
        }
    }

    //hh:mm:ss
    val simple: DateFormat = SimpleDateFormat("HH:mm")

    // Creating date from milliseconds
    // using Date() constructor

    // Creating date from milliseconds
    // using Date() constructor
    val start = Date(startTimeMilli)
    val end = Date(endTimeMilli)


    return simple.format(start) + " - " + simple.format(end) +", (" + calculateDurStr() + ")"
}