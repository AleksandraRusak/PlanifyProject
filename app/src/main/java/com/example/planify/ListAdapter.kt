package com.example.planify

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.databinding.TodoItemBinding
import com.example.planify.fragments.HomeFragmentDirections
import com.example.planify.view_model.TaskViewModel


// ListAdapter for displaying tasks in a RecyclerView
class ListAdapter : RecyclerView.Adapter<ListAdapter.ToDoViewHolder>() {
    // Creating a variable for all notes list
    private var taskList= emptyList<Task>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        // inflating a layout file for each task item of recycler view.
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }
    override fun getItemCount(): Int {
        // returning a list size
        return taskList.size
    }
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currentItem = taskList[position]
        with(holder) {

            binding.todoTask.text = currentItem.task

            binding.btnEditTask.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateSingleToDoFragment(currentItem)
                holder.itemView.findNavController().navigate(action)  }

            binding.btnDeleteTask.setOnClickListener {
                val taskViewModel = ViewModelProvider(
                    holder.itemView.findNavController()
                        .getViewModelStoreOwner(R.id.nav_graph))[TaskViewModel::class.java]
                taskViewModel.deleteTask(currentItem)
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

}