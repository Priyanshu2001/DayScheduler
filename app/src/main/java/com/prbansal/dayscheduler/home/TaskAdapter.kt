package com.prbansal.dayscheduler.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prbansal.dayscheduler.R
import com.prbansal.dayscheduler.database.DayTask
import com.prbansal.dayscheduler.databinding.TaskItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TaskAdapter(val taskAlarmListener : SetTaskAlarmListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(TaskDiffCallback()){
    private val ITEM_VIEW_TYPE_NO_TASK = -1
    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<DayTask>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.NoTask)
                else -> {
                    when(list.size){
                        0->listOf(DataItem.NoTask)
                        else ->listOf(DataItem.Header) + list.map { DataItem.TaskItem(it) }
                    }

                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class TaskViewHolder(val binding : TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(clickListner : SetTaskAlarmListener,item : DayTask){
            binding.dayTask = item
            binding.clickListener = clickListner
            when(item.alarmState){
                -1 -> binding.alarmBtn.visibility = View.GONE
                 0 ->binding.alarmBtn.visibility = View.VISIBLE
                1 -> {
                    binding.alarmBtn.visibility = View.VISIBLE
                    binding.alarmBtn.isChecked = true
                }

            }
            binding.executePendingBindings()
        }


        companion object{
            fun from(parent: ViewGroup) : TaskViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding : TaskItemBinding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskViewHolder(binding)
            }
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup, type: Int): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return when(type){
                    -1 ->TextViewHolder(layoutInflater.inflate(R.layout.no_task, parent, false))
                    0 -> TextViewHolder(layoutInflater.inflate(R.layout.header, parent, false))
                    else -> throw ClassCastException("Unknown viewType ${type}")
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_NO_TASK -> TextViewHolder.from(parent, ITEM_VIEW_TYPE_NO_TASK)
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent, ITEM_VIEW_TYPE_HEADER)
            ITEM_VIEW_TYPE_ITEM -> TaskViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> {
                val taskItem = getItem(position) as DataItem.TaskItem
                holder.bind(taskAlarmListener,taskItem.task)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.TaskItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.NoTask -> ITEM_VIEW_TYPE_NO_TASK
        }
    }


    class SetTaskAlarmListener(val clickListener: (task: DayTask) -> Unit) {
        fun onClick(task : DayTask) = clickListener(task)
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}
sealed class DataItem {
    data class TaskItem(val task: DayTask): DataItem() {
        override val id = task.taskId
    }

    object Header: DataItem() {
        override var id = Int.MIN_VALUE
    }

    object NoTask: DataItem() {
        override var id = -1
    }

    abstract val id :Int?
}


