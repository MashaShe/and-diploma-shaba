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
import com.example.and_diploma_shaba.databinding.FragmentFeedBinding
import com.example.and_diploma_shaba.databinding.FragmentRegistrationBinding
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.viewmodel.PostViewModel
import com.example.and_diploma_shaba.viewmodel.UserViewModel
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


        val adapter = UserAdapter(object : OnInteractionListener {

            @SuppressLint("ResourceType")
            override fun onLogin(user: User) {
                val enteredLogin = getString(R.id.editTextTextEmailAddress)
                val enteredPass = getString(R.id.editTextTextPassword)
                if (viewModel.isAuthorized(user.userId, enteredPass)) {
                    val bundle = Bundle().apply {
                        putString("AuthorName", user.userFirstName)
                    }
                    findNavController().navigate(
                        R.id.action__registrationFragment_to_feedFragment,
                        bundle
                    )
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.login_fail),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            @SuppressLint("ResourceType")
            override fun onRegister(user: User) {
                viewModel.addUser(user.copy(userLogin = getString(R.id.editTextTextEmailAddress), userPass = getString(R.id.editTextTextPassword)))
                Toast.makeText(
                    getActivity(),
                    getString(R.string.login_success),
                    Toast.LENGTH_SHORT
                ).show()

            }
        })

       return binding.root
    }
}


