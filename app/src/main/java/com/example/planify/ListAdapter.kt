package com.example.planify

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.databinding.TodoItemBinding
import com.example.planify.fragments.HomeFragmentDirections


class ListAdapter : RecyclerView.Adapter<ListAdapter.ToDoViewHolder>() {

    private var taskList= emptyList<Task>()

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

                binding.todoTask.text = currentItem.task

                binding.itemTaskLayout.setOnLongClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateSingleToDoFragment(currentItem)
                holder.itemView.findNavController().navigate(action)
                return@setOnLongClickListener true

                }
            }
        }


inner class ToDoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int) : Task {
        return taskList[position]
    }

}