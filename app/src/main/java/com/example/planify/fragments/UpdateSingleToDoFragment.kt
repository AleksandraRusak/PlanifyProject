package com.example.planify.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.planify.R
import com.example.planify.roomDB.Task
import com.example.planify.databinding.FragmentUpdateSingleToDoBinding
import com.example.planify.view_model.TaskViewModel


class UpdateSingleToDoFragment : Fragment() {

    private var _binding: FragmentUpdateSingleToDoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateSingleToDoFragmentArgs>()

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        _binding = FragmentUpdateSingleToDoBinding.inflate(inflater, container, false)

        binding.apply {
            updateTodoEt.setText(args.currentTask.task)

            updateTodoNextBtn.setOnClickListener {
                updateItem()
            }

            updateTodoClose.setOnClickListener {
                findNavController().navigate(R.id.action_updateSingleToDoFragment_to_homeFragment)
            }
        }
        return binding.root
    }

    private fun updateItem() {
        val task = binding.updateTodoEt.text.toString()

        if(inputCheck(task)) {
            val updatedTask = Task(args.currentTask.id, task, false)
            taskViewModel.updateTask(updatedTask)
            findNavController().navigate(R.id.action_updateSingleToDoFragment_to_homeFragment)
            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "The field can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(task: String) : Boolean {

        return !(TextUtils.isEmpty(task))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}