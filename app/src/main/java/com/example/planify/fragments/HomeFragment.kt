package com.example.planify.fragments

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
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

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskViewModel: TaskViewModel
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

            // // Initialize TaskViewModel to read data from database
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

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControl = findNavController()

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Fetch and display profile photo
        fetchAndDisplayProfilePhoto()

        // Set click listener on the ImageView
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
        navControl.navigate(R.id.action_homeFragment_to_splashFragment2)
    }


    private fun fetchAndDisplayProfilePhoto() {
        RetrofitInstance.api.getRandomProfilePhoto().enqueue(object : Callback<ProfilePhoto> {
            override fun onResponse(call: Call<ProfilePhoto>, response: Response<ProfilePhoto>) {
                if (response.isSuccessful) {
                    response.body()?.let { profilePhoto ->
                        Glide.with(this@HomeFragment)
                            .load(profilePhoto.urls.regular)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}




