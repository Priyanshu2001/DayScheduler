package com.prbansal.dayscheduler.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DayDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insert(dayTask: DayTask)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param dayTask new value to write
     */
    @Update
     fun update(dayTask: DayTask)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from day_task_table WHERE taskId = :key")
    fun get(key: Int): DayTask?



    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM day_task_table ORDER BY taskId ASC")
    fun getAllTasks(): LiveData<List<DayTask>>

    /**
     * Selects and returns the latest dayTask.
     */


}

