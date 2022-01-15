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
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.adapter.OnInteractionListener
import com.example.and_diploma_shaba.adapter.PostsAdapter
import com.example.and_diploma_shaba.adapter.UserAdapter
//import com.example.and_diploma_shaba.adapter.UserAdapter
import com.example.and_diploma_shaba.databinding.FragmentFeedBinding
import com.example.and_diploma_shaba.databinding.FragmentRegistrationBinding
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.viewmodel.UserViewModel
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.viewmodel.AuthViewModel


import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_registration.*


class RegistrationFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        FirebaseMessaging.getInstance().token.addOnSuccessListener {
//            println("ТОКЕН: " + it)
//        }
//        setContentView(R.layout.fragment_registration)
////        val view: View = inflater.inflate(R.layout.testclassfragment, container, false)
////        Button loginButton =(Button)findViewById(R.id.loginButton)
//        findViewById<Button>(R.id.loginButton).setOnClickListener {
//            //Toast.makeText(this,"СПАСИБО!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this@RegistrationFragment, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }


    private val viewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegistrationBinding.inflate(
            inflater,
            container,
            false
        )
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//       val binding = FragmentRegistrationBinding.bind(view)
        //val binding = FragmentRegistrationBinding.inflate(layoutInflater)
        with(binding) {

            val initialUser = User(
            userId = 0L,
            "testuser",
            "1",
            null,
            "Anonymous",
            "Anonymous"//,
            // null,
            // userAuthorities = listOf("ROLE_ANONYMOUS")
        )
            viewModel.addUser(initialUser)

            binding.userFirstName.text = "TESTISHE"
            binding.userLastName.text = initialUser.userPass

            binding.loginButton.setOnClickListener {
                val enteredLogin = binding.editTextTextEmailAddress.text
                val enteredPass = binding.editTextTextPassword.text
               // val user: User = viewModel.authorization(enteredLogin.toString())
               // val users2: List<User>? = users.value
              //  val user:User? = users2?.first()
               // val user: User
               // user = viewModel.authorization(enteredLogin.toString())
//                if (user.isNullOrEmpty()) {
//                    Toast.makeText(
//                        activity,
//                        getString(R.string.login_fail),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
              //  if (user?.userLogin == enteredLogin.toString() && user?.userPass == enteredPass.toString()) {
                    if (viewModel.isAuthorized(viewModel.userIdByLogin(enteredLogin.toString()), enteredPass.toString())) {

                        val bundle = Bundle().apply {
                            putString("AuthorLogin", enteredLogin.toString())
                        }
                        findNavController().navigate(
                            R.id.action__registrationFragment_to_feedFragment,
                            bundle
                        )
                        Toast.makeText(
                            activity,
                            "вошли",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Toast.makeText(
                        activity,
                        "не вошли, но кнопка отработала",
                        Toast.LENGTH_SHORT
                    ).show()


//                }
            }

            binding.regButton.setOnClickListener {
                val enteredLogin = binding.editTextTextEmailAddress.text
                val enteredPass = binding.editTextTextPassword.text
                val userToAdd = User(
                    userId = 0L,
                    enteredLogin.toString(),
                    enteredPass.toString(),
                    null,
                    "Anonymous",
                    "Anonymous"//,
                    // null,
                    // userAuthorities = listOf("ROLE_ANONYMOUS")
                )

//                if (user.isNullOrEmpty()) {
//                    Toast.makeText(
//                        activity,
//                        getString(R.string.login_fail),
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                } else {
                  //  userToAdd = user.first().copy(userLogin = enteredLogin.toString(), userPass = enteredPass.toString() )
                    viewModel.addUser(userToAdd)
                    Toast.makeText(
                        activity,
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                //}

            }

        }
        val adapter = UserAdapter(object : OnInteractionListener {
                    })
       return binding.root
        viewModel.data.observe(viewLifecycleOwner,{ users ->
        adapter.submitList(users)
    })
    }
}

//--------------------commented below - uncomment if you werw wrong
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
//        override fun onLogin(user: User) {
//            val enteredLogin = binding.editTextTextEmailAddress.text.toString()
//            val enteredPass = binding.editTextTextPassword.text.toString()
//            if (viewModel.isAuthorized(user.userId, enteredPass)) {
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
//        override fun onRegister(user: User) {
//            viewModel.addUser(user.copy(userLogin = getString(R.id.editTextTextEmailAddress), userPass = getString(R.id.editTextTextPassword)))
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
//
//       return binding.root
//    }
//}
//
//
