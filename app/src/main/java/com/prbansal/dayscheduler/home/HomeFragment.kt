package com.prbansal.dayscheduler.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prbansal.dayscheduler.R
import com.prbansal.dayscheduler.database.DayDatabase
import com.prbansal.dayscheduler.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding  :FragmentHomeBinding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DayDatabase.getInstance(application).dayDatabaseDao

        val viewModelFactory = HomeViewModelFactory(dataSource, application)

        val homeViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = homeViewModel

        // binding.setLifecycleOwner(this)
        binding.lifecycleOwner = this

        val adapter = TaskAdapter(TaskAdapter.SetTaskAlarmListener { task->
            homeViewModel.onAlarmChecked(task)
        })

        binding.tasksRv.adapter = adapter

        homeViewModel.allTasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })
        
        
        return binding.root
    }
    
}