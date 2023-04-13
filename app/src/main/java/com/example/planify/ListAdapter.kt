package com.example.planify

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planify.databinding.TodoItemBinding

class ListAdapter(private val taskList: MutableList<ToDoData>)  : RecyclerView.Adapter<ListAdapter.ToDoViewHolder>() {

    class ToDoViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val TAG = "TaskAdapter"
    private var listener:ListAdapterInterface? = null
    fun setListener (listener:ListAdapterInterface){
        this.listener = listener
    }

    interface ListAdapterInterface {
        fun onDeleteItemClicked(toDoData: ToDoData, position : Int)
        fun onEditItemClicked(toDoData: ToDoData , position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder) {
            with(taskList[position]) {

                binding.todoTask.text = this.task

                Log.d(TAG, "onBindViewHolder: " + this)
                binding.editTask.setOnClickListener {
                    listener?.onEditItemClicked(this, position)
                }

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(this, position)
                }
            }
        }
    }

}