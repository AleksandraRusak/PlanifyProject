package com.example.planify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.planify.ToDoData
import com.example.planify.databinding.FragmentAddSingleToDoBinding
import com.google.android.material.textfield.TextInputEditText


class AddSingleToDoFragment : DialogFragment() {

    private lateinit var binding: FragmentAddSingleToDoBinding
    private var listener: DialogNextBtnClickListeners? = null
    private var toDoData: ToDoData? = null

    fun setListener(listener : DialogNextBtnClickListeners){
        this.listener = listener
    }

    companion object {
        const val TAG = "DialogFragment"
        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            AddSingleToDoFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddSingleToDoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){

            toDoData = ToDoData(
                arguments?.getString("taskId").toString() ,
                arguments?.getString("task").toString())
            binding.todoEt.setText(toDoData?.task)
        }

        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if (toDoData == null) {
                    listener?.onSaveTask(todoTask, binding.todoEt)
                } else {
                    toDoData!!.task = todoTask
                    listener?.updateTask(toDoData!!, binding.todoEt)
                }


                binding.todoClose.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    interface DialogNextBtnClickListeners{
        fun onSaveTask(todo: String, todoEt : TextInputEditText)
        fun updateTask(toDoData: ToDoData , todoEdit:TextInputEditText)
    }

}