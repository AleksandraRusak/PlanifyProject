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
import com.example.planify.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    // Declare private variables
    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize FirebaseAuth and NavController
        init(view)

        // Navigate to sign in fragment when "Sign In" text is clicked
        binding.tvSignIn.setOnClickListener{
            navControl.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        // Register the user when "Register" button is clicked
        binding.btnRegister.setOnClickListener {
            // Get email, password, and confirm password from the input fields
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                // Check if the password matches the confirm password
                if (password == confirmPassword) {

                    // Show a message and register the user if the input is valid
                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                    registerUser(email, password)

                } else {
                    Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Register the user with email and password
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                navControl.navigate(R.id.action_signUpFragment_to_signInFragment)
            } else {
                Toast.makeText(context, "Sign Up failed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // Initialize navigation controller and Firebase authentication
    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }


}