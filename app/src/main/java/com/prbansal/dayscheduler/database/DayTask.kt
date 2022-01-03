package com.prbansal.dayscheduler.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_task_table")
data class DayTask (
    @PrimaryKey(autoGenerate = true)
    val taskId: Int?,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long,

    @ColumnInfo(name = "end_time_milli")
    val endTimeMilli: Long,

    @ColumnInfo(name = "alarm_state")
    var alarmState: Int? = -1,

    @ColumnInfo(name = "title")
    val title: String
)