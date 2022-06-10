package com.example.and_diploma_shaba.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.databinding.FragmentRegistrationBinding
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.util.AndroidUtils
import com.example.and_diploma_shaba.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject
import com.example.and_diploma_shaba.activity.utils.Dialog
import com.example.and_diploma_shaba.activity.utils.showAuthResultDialog
import com.example.and_diploma_shaba.activity.utils.showLoginAuthDialog

@AndroidEntryPoint
//@WithFragmentBindings

class RegistrationFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels(
        //ownerProducer = ::requireParentFragment
    )
    @Inject
    lateinit var appAuth: AppAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.authData.observe(viewLifecycleOwner) {
           // invalidateOptionsMenu(this)
        }
        val binding = FragmentRegistrationBinding.inflate(
            inflater,
            container,
            false
        )

//        with(binding) {
//            loginButton.setOnClickListener {
//                val enteredLogin = editTextTextEmailAddress.text.toString()
//                val enteredPass = editTextTextPassword.text.toString()
//                viewModel.selectMyPage()
//                appAuth.authUser(enteredLogin, enteredPass){
//                  //  showAuthResultDialog(it)
//                    viewModel.markMyPageAlreadyOpened()
//                    Toast.makeText(
//                        activity,
//                        "Залогинен!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//            }
//            regButton.setOnClickListener {
//                val enteredLogin = editTextTextEmailAddress.text.toString()
//                val enteredPass = editTextTextPassword.text.toString()
//                 appAuth.newUserRegistration(enteredLogin,enteredPass,"Test","http://ya.ru"){
//                     viewModel.markMyPageAlreadyOpened()
//                     Toast.makeText(
//                         activity,
//                         getString(R.string.login_success),
//                         Toast.LENGTH_SHORT
//                     ).show()
//            }
//
//            }
//        }

        return binding.root
    }

}



//                // Доступа к id у нас нет из UI, поэтому логин вместо id
//                if (viewModel.isAuthorized(enteredLogin, enteredPass)) {
//                    AppAuth.getInstance()
//                    val bundle = Bundle().apply {
//                        // TODO Где взять имя?)
//                        //putString("AuthorName", user.userFirstName)
//                    }
//                    AndroidUtils.hideKeyboard(root)
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
//                }
//            }





//package com.example.and_diploma_shaba.activity
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.LiveData
//import androidx.navigation.fragment.findNavController
//import com.example.and_diploma_shaba.R
//import com.example.and_diploma_shaba.adapter.OnInteractionListener
//import com.example.and_diploma_shaba.adapter.PostsAdapter
//import com.example.and_diploma_shaba.adapter.UserAdapter
////import com.example.and_diploma_shaba.adapter.UserAdapter
//import com.example.and_diploma_shaba.databinding.FragmentFeedBinding
//import com.example.and_diploma_shaba.databinding.FragmentRegistrationBinding
//import com.example.and_diploma_shaba.dto.Post
//import com.example.and_diploma_shaba.viewmodel.UserViewModel
//import com.example.and_diploma_shaba.dto.User
//import com.example.and_diploma_shaba.viewmodel.AuthViewModel
//
//
//import com.google.firebase.messaging.FirebaseMessaging
//import kotlinx.android.synthetic.main.fragment_registration.*
//
//
//class RegistrationFragment : Fragment() {
//    private val viewModel: UserViewModel by viewModels(
//        ownerProducer = ::requireParentFragment
//    )
//
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
//        with(binding) {
//
//            val initialUser = User(
//            userId = 0L,
//            "testuser",
//            "1",
//            null,
//            "Anonymous",
//            "Anonymous"//,
//            // null,
//            // userAuthorities = listOf("ROLE_ANONYMOUS")
//        )
//            viewModel.addUser(initialUser)
//
//            binding.userFirstName.text = "TESTISHE"
//            binding.userLastName.text = initialUser.userPass
//
//            binding.loginButton.setOnClickListener {
//                val enteredLogin = binding.editTextTextEmailAddress.text
//                val enteredPass = binding.editTextTextPassword.text
//
//                    if (viewModel.isAuthorized(viewModel.userIdByLogin(enteredLogin.toString()), enteredPass.toString())) {
//
//                        val bundle = Bundle().apply {
//                            putString("AuthorLogin", enteredLogin.toString())
//                        }
//                        findNavController().navigate(
//                            R.id.action__registrationFragment_to_feedFragment,
//                            bundle
//                        )
//                        Toast.makeText(
//                            activity,
//                            "вошли",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    Toast.makeText(
//                        activity,
//                        "не вошли, но кнопка отработала",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//
////                }
//            }
//
//            binding.regButton.setOnClickListener {
//                val enteredLogin = binding.editTextTextEmailAddress.text
//                val enteredPass = binding.editTextTextPassword.text
//                val userToAdd = User(
//                    userId = 0L,
//                    enteredLogin.toString(),
//                    enteredPass.toString(),
//                    null,
//                    "Anonymous",
//                    "Anonymous"//,
//                )
//
//                    viewModel.addUser(userToAdd)
//                    Toast.makeText(
//                        activity,
//                        getString(R.string.login_success),
//                        Toast.LENGTH_SHORT
//                    ).show()
//            }
//
//        }
//        val adapter = UserAdapter(object : OnInteractionListener {
//                    })
//       return binding.root
//        viewModel.data.observe(viewLifecycleOwner,{ users ->
//        adapter.submitList(users)
//    })
//    }
//}
