package com.example.planify.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.planify.R
import com.example.planify.roomDB.Task
import com.example.planify.databinding.FragmentAddSingleToDoBinding
import com.example.planify.view_model.TaskViewModel


class AddSingleToDoFragment : Fragment() {

    private var _binding: FragmentAddSingleToDoBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddSingleToDoBinding.inflate(inflater, container, false)

        binding.apply {
            taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]


            binding.todoNextBtn.setOnClickListener {
                insertToDB()
            }

            binding.todoClose.setOnClickListener {
                findNavController().navigate(R.id.action_addSingleToDoFragment_to_homeFragment)
            }

        }

        return binding.root
    }


    // insert task to database
    private fun insertToDB() {
        val task = binding.todoEt.text.toString()

        if(inputCheck (task)) {
            val todo = Task(0, task, false)
            taskViewModel.addTask(todo)

            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addSingleToDoFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "The fields can not be empty!", Toast.LENGTH_SHORT).show()
        }

    }

    // if text input fields aren't empty
    private fun inputCheck(task: String) : Boolean {
        return !(TextUtils.isEmpty(task))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    }
