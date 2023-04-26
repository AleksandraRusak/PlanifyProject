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
import com.example.planify.R
import com.example.planify.databinding.FragmentHomeBinding
import com.example.planify.view_model.TaskViewModel
import com.google.firebase.auth.FirebaseAuth



class HomeFragment : Fragment(), SearchView.OnQueryTextListener{

    // View binding instance for the fragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // TaskViewModel to read and manage data from/to the database
    private lateinit var taskViewModel: TaskViewModel
    // Adapter for the RecyclerView
    private lateinit var adapter: ListAdapter

    private lateinit var navControl: NavController
    private lateinit var firebaseAuth: FirebaseAuth


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

            // Initialize TaskViewModel to read data from database
            taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
            taskViewModel.readAllData.observe(viewLifecycleOwner) { task ->
                adapter.setData(task)
            }

            // Add a task button
            btnAddTask.setOnClickListener {
                navControl.navigate(R.id.action_homeFragment_to_addSingleToDoFragment)
            }

            // Delete all tasks button
            btnDeleteAllToDo.setOnClickListener {
                deleteAllTasks()
            }

            // Log out button
            btnLogOut.setOnClickListener {
                logOutUser()
            }

            btnProfile.setOnClickListener {
                navControl.navigate(R.id.action_homeFragment_to_profilePhotoFragment)
            }

        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControl = findNavController()

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

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


    // Log out user
    private fun logOutUser() {
        firebaseAuth.signOut()
        navControl.navigate(R.id.action_homeFragment_to_signInFragment)
    }

    //This function is called when the fragment is being destroyed
    //It sets the _binding variable to null to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}




