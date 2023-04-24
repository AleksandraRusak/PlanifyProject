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
import com.bumptech.glide.Glide
import com.example.planify.ListAdapter
import com.example.planify.R
import com.example.planify.databinding.FragmentHomeBinding
import com.example.planify.profilePhotoAPI.ProfilePhoto
import com.example.planify.profilePhotoAPI.RetrofitInstance
import com.example.planify.view_model.TaskViewModel
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    // Current profile photo url to display the user's profile photo
    private var currentProfilePhotoUrl: String? = null



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

        }

        // Restore saved state (if any) for the current profile photo url
            savedInstanceState?.let { savedState ->
                currentProfilePhotoUrl = savedState.getString("currentProfilePhotoUrl")
            }

        // Display the user's profile photo
            if (currentProfilePhotoUrl != null) {
                Glide.with(this)
                    .load(currentProfilePhotoUrl)
                    .circleCrop()
                    .into(binding.profilePhoto)
            } else {
                fetchAndDisplayProfilePhoto()
            }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControl = findNavController()

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Set click listener on the ImageView to fetch and display the user's profile photo
        binding.profilePhoto.setOnClickListener {
            fetchAndDisplayProfilePhoto()
        }
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


    // Fetch and display the user's profile photo
    private fun fetchAndDisplayProfilePhoto() {
        RetrofitInstance.api.getRandomProfilePhoto().enqueue(object : Callback<ProfilePhoto> {
            override fun onResponse(call: Call<ProfilePhoto>, response: Response<ProfilePhoto>) {
                if (response.isSuccessful) {
                    response.body()?.let { profilePhoto ->
                        currentProfilePhotoUrl = profilePhoto.urls.regular
                        Glide.with(this@HomeFragment)
                            .load(currentProfilePhotoUrl)
                             //rounded corners
                            .circleCrop()
                            .into(binding.profilePhoto)
                    }
                }
            }

            override fun onFailure(call: Call<ProfilePhoto>, t: Throwable) {
                // Handle error here
            }
        })
    }

    //Method to save the instance state of the fragment
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentProfilePhotoUrl", currentProfilePhotoUrl)
    }


    //This function is called when the fragment is being destroyed
    //It sets the _binding variable to null to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}




