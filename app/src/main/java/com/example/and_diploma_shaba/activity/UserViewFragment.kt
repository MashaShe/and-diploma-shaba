package com.example.and_diploma_shaba.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.adapter.OnInteractionListener
import com.example.and_diploma_shaba.adapter.PostsAdapter
import com.example.and_diploma_shaba.adapter.UserAdapter
//import com.example.and_diploma_shaba.adapter.UserAdapter
import com.example.and_diploma_shaba.databinding.FragmentFeedBinding
import com.example.and_diploma_shaba.databinding.FragmentRegistrationBinding
//import com.example.and_diploma_shaba.viewmodel.UserViewModel
import com.example.and_diploma_shaba.dto.User


import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_registration.*
@AndroidEntryPoint

class UserViewFragment : Fragment() {
//
//    private val viewModel: UserViewModel by viewModels(
//        ownerProducer = ::requireParentFragment
//    )
//
////--------------------commented below - uncomment if you werw wrong
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding = FragmentRegistrationBinding.inflate(
//            inflater,
//            container,
//            false
//        )
//
//        val initialUser = User(
//            userId = 0L,
//            "testuser",
//            "1",
//            null,
//            "Anonymous",
//            "Anonymous"//,
//            // null,
//            // userAuthorities = listOf("ROLE_ANONYMOUS")
//        )
//        viewModel.addUser(initialUser)
//
//
//        //@SuppressLint("ResourceType")
//         fun onLogin(user: User) {
//            val enteredLogin = binding.editTextTextEmailAddress.text.toString()
//            val enteredPass = binding.editTextTextPassword.text.toString()
//            if (viewModel.isAuthorized(enteredLogin, enteredPass)) {
//                val bundle = Bundle().apply {
//                    putString("AuthorName", user.userFirstName)
//                }
//                findNavController().navigate(
//                    R.id.action__registrationFragment_to_feedFragment,
//                    bundle
//                )
//            } else {
//                Toast.makeText(
//                    activity,
//                    getString(R.string.login_fail),
//                    Toast.LENGTH_SHORT
//                ).show()
//
//            }
//        }
//
//        @SuppressLint("ResourceType")
//         fun onRegister(user: User) {
//        //    viewModel.addUser(user.copy(userLogin = getString(R.id.editTextTextEmailAddress), userPass = getString(R.id.editTextTextPassword)))
//            Toast.makeText(
//                getActivity(),
//                getString(R.string.login_success),
//                Toast.LENGTH_SHORT
//            ).show()
//
//        }
//
//        val adapter = UserAdapter(object : OnInteractionListener {
//
//
//            //@SuppressLint("ResourceType")
//            override fun onLogin(user: User) {
//                val enteredLogin = binding.editTextTextEmailAddress.text.toString()
//                val enteredPass = binding.editTextTextPassword.text.toString()
//                if (viewModel.isAuthorized(user.userId, enteredPass)) {
//                    val bundle = Bundle().apply {
//                        putString("AuthorName", user.userFirstName)
//                    }
//                    findNavController().navigate(
//                        R.id.action__registrationFragment_to_feedFragment,
//                        bundle
//                    )
//                } else {
//                    Toast.makeText(
//                        activity,
//                        getString(R.string.login_fail),
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                }
//            }
//
//            @SuppressLint("ResourceType")
//            override fun onRegister(user: User) {
//                viewModel.addUser(user.copy(userLogin = getString(R.id.editTextTextEmailAddress), userPass = getString(R.id.editTextTextPassword)))
//                Toast.makeText(
//                    getActivity(),
//                    getString(R.string.login_success),
//                    Toast.LENGTH_SHORT
//                ).show()
//
//            }
//        })
////        binding.list.adapter = adapter
////        viewModel.data.observe(viewLifecycleOwner, { posts ->
////            adapter.submitList(posts)
////        })
//       return binding.root
//    }
}


