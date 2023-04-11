package com.example.planify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.planify.databinding.FragmentAddSingleToDoBinding
import com.google.android.material.textfield.TextInputEditText


class AddSingleToDoFragment : DialogFragment() {

    private lateinit var binding: FragmentAddSingleToDoBinding
    private lateinit var listener: DialogNextBtnClickListeners

    fun setListener(listener : DialogNextBtnClickListeners){
        this.listener = listener
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

        registerEvents()
    }

    private fun registerEvents(){
        binding.todoNextBtn.setOnClickListener {
           val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()){
                listener.onSaveTask(todoTask, binding.todoEt)

            }else{
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }

    }

    interface DialogNextBtnClickListeners{
        fun onSaveTask(todo: String, todoEt : TextInputEditText)
    }

}