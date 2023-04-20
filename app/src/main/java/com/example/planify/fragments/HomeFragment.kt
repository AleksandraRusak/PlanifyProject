package com.example.planify.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planify.ListAdapter
import com.example.planify.databinding.FragmentHomeBinding
import com.example.planify.view_model.TaskViewModel
import com.google.firebase.database.*

class HomeFragment : Fragment(), SearchView.OnQueryTextListener{

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: ListAdapter

    private lateinit var navControl: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.apply {
            // Setup RecyclerView
            adapter = ListAdapter()
            val recyclerView = binding.mainRecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // // Initialize TaskViewModel to read data from database
            taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
            taskViewModel.readAllData.observe(viewLifecycleOwner) { task ->
                adapter.setData(task)
            }

            // Add new task button
            btnAddTask.setOnClickListener {
                //findNavController().navigate()
            }


            // Delete all tasks button
            btnDeleteAllToDo.setOnClickListener {
                deleteAllTasks()
            }
        }

        return binding.root

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
        }

        // delete all tasks
        private fun deleteAllTasks() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Delete") { _, _ ->
                taskViewModel.deleteAllTasks()
            }
            builder.setNegativeButton("Cancel") { _, _ -> }
            builder.setTitle( "Delete All Tasks?")
            builder.create().show()
        }

        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
}




