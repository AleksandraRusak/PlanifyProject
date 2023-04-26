package com.example.planify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.planify.R
import com.example.planify.databinding.FragmentProfilePhotoBinding
import com.example.planify.profilePhotoAPI.ProfilePhoto
import com.example.planify.profilePhotoAPI.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfilePhotoFragment : Fragment() {

    // View binding instance for the fragment
    private var _binding: FragmentProfilePhotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var navControl: NavController

    // Current profile photo url to display the user's profile photo
    private var currentProfilePhotoUrl: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfilePhotoBinding.inflate(inflater, container, false)

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

        // Initialize navControl
        navControl = findNavController()

        // Set click listener on the ImageView to fetch and display the user's profile photo
        binding.profilePhoto.setOnClickListener {
            fetchAndDisplayProfilePhoto()
        }

        binding.btnBack.setOnClickListener {
            navControl.navigate(R.id.action_profilePhotoFragment_to_homeFragment)
        }

    }


    // Fetch and display the user's profile photo
    private fun fetchAndDisplayProfilePhoto() {
        RetrofitInstance.api.getRandomProfilePhoto().enqueue(object : Callback<ProfilePhoto> {
            override fun onResponse(call: Call<ProfilePhoto>, response: Response<ProfilePhoto>) {
                if (response.isSuccessful) {
                    Log.d("ProfilePhotoFragment", "Profile photo fetched successfully")
                    response.body()?.let { profilePhoto ->
                        currentProfilePhotoUrl = profilePhoto.urls.regular
                        Log.d("ProfilePhotoFragment", "URL: $currentProfilePhotoUrl")
                        Glide.with(this@ProfilePhotoFragment)
                            .load(currentProfilePhotoUrl)
                            //rounded corners
                            .circleCrop()
                            .into(binding.profilePhoto)
                    }
                } else {
                    Log.e("ProfilePhotoFragment", "Error fetching profile photo. Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProfilePhoto>, t: Throwable) {
                Log.e("ProfilePhotoFragment", "Error fetching profile photo: ${t.message}")
            }
        })
    }

    //Method to save the instance state of the fragment
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentProfilePhotoUrl", currentProfilePhotoUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}