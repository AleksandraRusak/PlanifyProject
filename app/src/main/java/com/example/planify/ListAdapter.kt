package com.example.planify

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.databinding.TodoItemBinding
import com.example.planify.fragments.HomeFragmentDirections


// ListAdapter for displaying tasks in a RecyclerView
class ListAdapter : RecyclerView.Adapter<ListAdapter.ToDoViewHolder>() {

    private var taskList= emptyList<Task>()
    private lateinit var navControl: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currentItem = taskList[position]
        with(holder) {

                // Set the task text
                binding.todoTask.text = currentItem.task

                // new 20 april
                binding.btnEditTask.setOnClickListener{
                    navControl.navigate(R.id.action_homeFragment_to_updateSingleToDoFragment)
                }

                 binding.btnDeleteTask.setOnClickListener {

                  }

                binding.itemTaskLayout.setOnLongClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateSingleToDoFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
                return@setOnLongClickListener true

                }
            }
        }


    // ViewHolder class for holding task items
inner class ToDoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    // Update the task list and notify the adapter
    @SuppressLint("NotifyDataSetChanged")
    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    // Get the task at the specified position
    fun getNoteAt(position: Int) : Task {
        return taskList[position]
    }

}