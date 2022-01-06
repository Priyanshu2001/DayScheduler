package com.prbansal.dayscheduler.home

import android.app.Application
import androidx.lifecycle.*
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

    private val _navigateToTaskAdder = MutableLiveData<Boolean?>()
    val navigateToTaskAdder: LiveData<Boolean?>
        get() = _navigateToTaskAdder


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



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
        _navigateToTaskAdder.value = true
    }
    fun onSaveTaskClicked(newTask : DayTask){
        uiScope.launch {
            insert(newTask)
        }
    }

    fun doneNavigating() {
        _navigateToTaskAdder.value = null
    }

    fun onAlarmChecked(dayTask: DayTask) {

        when(dayTask.alarmState) {
            0-> dayTask.alarmState = 1
            else -> dayTask.alarmState = 0
        }
        
        uiScope.launch {
           update(dayTask)
        }
    }



}