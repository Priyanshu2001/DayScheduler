package com.prbansal.dayscheduler.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.prbansal.dayscheduler.databinding.FragmentAddTaskBinding
import com.prbansal.dayscheduler.R
import com.prbansal.dayscheduler.database.DayDatabase
import com.prbansal.dayscheduler.database.DayTask
import com.prbansal.dayscheduler.getAlarmStateByType


class AddTaskFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      val binding = FragmentAddTaskBinding.inflate(inflater, container, false)


        val application = requireNotNull(this.activity).application
        val dataSource = DayDatabase.getInstance(application).dayDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource, application)

        val homeViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(HomeViewModel::class.java)

        val toolbar = binding.toolbar
        toolbar.setNavigationOnClickListener { v -> dismiss()}
        toolbar.setTitle("Add Task Details")




        toolbar.setOnMenuItemClickListener { item ->
            onSaveClicked(binding, homeViewModel)
            dismiss()
            true
        }
      return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }


    fun getTaskType(type : Int) : Int {
        return when (type) {
            R.id.task_sleep -> 1
            R.id.task_break -> 2
            R.id.task_study -> 3
            R.id.task_hobby -> 4
            R.id.task_eat -> 5
            R.id.task_entrainment -> 6
            R.id.task_fitness -> 7
            else -> 0
        }
    }

    fun onSaveClicked(binding: FragmentAddTaskBinding, homeViewModel: HomeViewModel) {
        val type = getTaskType(binding.selectTaskType.checkedChipId)
        val name = binding.taskName.text.toString()
        val newTask = DayTask(null, System.currentTimeMillis() + 10000,
            System.currentTimeMillis() + 10000000, getAlarmStateByType(type), name, type)
        homeViewModel.onSaveTaskClicked(newTask)
    }
}