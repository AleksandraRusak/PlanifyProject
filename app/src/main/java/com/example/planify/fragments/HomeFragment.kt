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
import com.example.planify.ListAdapter
import com.example.planify.databinding.FragmentHomeBinding
import com.example.planify.view_model.TaskViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment(), SearchView.OnQueryTextListener{

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var navControl: NavController

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel


    // for the searchView
    //private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {

            // recyclerView
            //this adapter is for retrieving the recyclerView
            val adapter = ListAdapter()
            //val recyclerView = recyclerView
            //recyclerView.adapter = adapter
           //recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // taskViewModel to read data from database
            taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
            taskViewModel.readAllData.observe(viewLifecycleOwner) { task ->
                adapter.setData(task)
            }

            // add buttons

            btnAddToDo.setOnClickListener {
                //findNavController().navigate(R.id.action_homeFragment_to_addSingleToDo)
            }

            // delete all tasks button
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




