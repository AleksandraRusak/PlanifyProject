package com.example.planify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.planify.R
import com.example.planify.databinding.FragmentHomeBinding
import com.example.planify.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment(), AddSingleToDoFragment.DialogNextBtnClickListeners {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var singleToDoFragment : AddSingleToDoFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)
        registerEvents()

    }

    private fun registerEvents(){
        binding.btnAddToDo.setOnClickListener {
            singleToDoFragment = AddSingleToDoFragment()
            singleToDoFragment.setListener(this)
            singleToDoFragment.show(
                childFragmentManager,
                "AddSingleToDoFragment"
            )

        }

    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        TODO("Not yet implemented")
    }

}