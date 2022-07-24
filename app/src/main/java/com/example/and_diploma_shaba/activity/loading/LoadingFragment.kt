package com.example.and_diploma_shaba.activity.loading

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.databinding.FragmentLoadingBinding
import com.example.and_diploma_shaba.repository.AppNetState
import com.example.and_diploma_shaba.repository.AuthMethods
//import com.example.and_diploma_shaba.viewmodel.EventAllViewModel
//import com.example.and_diploma_shaba.viewmodel.PostAllViewModel
import com.example.and_diploma_shaba.viewmodel.UsersViewModel
import javax.inject.Inject
import com.example.and_diploma_shaba.activity.MainActivity
import com.example.and_diploma_shaba.viewmodel.PostAllViewModel


@AndroidEntryPoint
class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private val usersVM: UsersViewModel by activityViewModels()
    private val postsVM: PostAllViewModel by activityViewModels()
   // private val eventsVM: EventAllViewModel by activityViewModels()

    @Inject
    lateinit var appAuth: AppAuth

    @Inject
    lateinit var repoNetwork: AuthMethods


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoadingBinding.bind(view)
        with (binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                when (repoNetwork.checkConnection()) {

                    AppNetState.NO_INTERNET -> displayError(R.string.error_dialog_auth_internet)
                    AppNetState.NO_SERVER_CONNECTION -> displayError(R.string.error_dialog_auth_server)
                    AppNetState.CONNECTION_ESTABLISHED -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.loading.setText(R.string.error_dialog_auth_server)
                        }, 6000)
                        appAuth.checkAmLogined()
                        usersVM.loadUsers()
                        postsVM.loadPosts()
                       // eventsVM.loadEvents()
                        requireActivity().runOnUiThread {
                            Handler(Looper.getMainLooper()).postDelayed({
                                startActivity(
                                    Intent(requireActivity(), MainActivity::class.java)
                                )
                                requireActivity().finish()
                            }, 2000)

                        }

                    }
                }
            }
        }

        binding.proceed.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun FragmentLoadingBinding.displayError(errorT: Int) {
        val handler = Handler(Looper.getMainLooper());
        handler.post {
                groupError.isVisible = true
                loading.setText(errorT)
               // loAnimation.pauseAnimation()

        }
    }
}
