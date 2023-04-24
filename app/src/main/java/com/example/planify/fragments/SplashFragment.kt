package com.example.planify.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.planify.R
import com.example.planify.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    // declare a private variable for FragmentSplashBinding
    private lateinit var binding: FragmentSplashBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)

            // after 3 seconds, navigate from SplashFragment to SignUpFragment
            Handler().postDelayed({
                findNavController().navigate(R.id.action_splashFragment2_to_signUpFragment)
            }, 3000)

        return binding.root
    }
}


