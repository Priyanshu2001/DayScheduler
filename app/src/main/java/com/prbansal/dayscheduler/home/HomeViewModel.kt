package com.prbansal.dayscheduler.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prbansal.dayscheduler.database.DayDatabaseDao
import com.prbansal.dayscheduler.database.DayTask
import kotlinx.coroutines.*

class HomeViewModel(
    val database : DayDatabaseDao,
     application: Application) : AndroidViewModel(application) {

  //  private var dayTask = MutableLiveData<DayTask?>()

    val allTasks = database.getAllTasks()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)



    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
   /* private suspend fun getTotaskFromDatabase(): DayTask? {
        //return withContext(Dispatchers.IO) {
        var task = database.getTodayTask()
        if (task?.endTimeMilli != task?.startTimeMilli) {
            task = null
        }
        return task
        //}
    }*/



    private suspend fun update(task: DayTask) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun insert(task: DayTask) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

    fun onAddClicked() {
        uiScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newTask = DayTask(null, System.currentTimeMillis() + 10000, System.currentTimeMillis() + 10000000, 0, "Hello")

            insert(newTask)
        }
    }

    fun onAlarmChecked(dayTask: DayTask) {
        viewModelScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.

            when(dayTask.alarmState) {
                0-> dayTask.alarmState = 1
                else -> dayTask.alarmState = 0
            }

           update(dayTask)
        }
    }



}