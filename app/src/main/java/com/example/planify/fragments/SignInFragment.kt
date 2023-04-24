package com.example.planify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.planify.R
import com.example.planify.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    // Declare private variables
    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize FirebaseAuth and NavController
        init(view)

        // Navigate to sign up fragment when "Sign Up" text is clicked
        binding.tvSignUp.setOnClickListener {
            navControl.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // Log in the user with email and password when "Log In" button is clicked
        binding.btnLogin.setOnClickListener {
            // Get email and password from the input fields
            val email = binding.etEmailUser.text.toString().trim()
            val password = binding.etPasswordUser.text.toString().trim()

            // Check if email and password are not empty, then log in the user
            if (email.isNotEmpty() && password.isNotEmpty()){

                loginUser(email, password)
            } else {
                // Show a message if email or password are empty
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Log in the user with email and password
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Login successfully", Toast.LENGTH_SHORT).show()
                navControl.navigate(R.id.action_signInFragment_to_homeFragment)
            }else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Initialize navigation controller and Firebase authentication
    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }


}