package com.example.and_diploma_shaba.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.activity.NewPostFragment.Companion.textArg
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.viewmodel.AuthViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.and_diploma_shaba.viewmodel.*
import com.example.and_diploma_shaba.activity.utils.Dialog
import com.example.and_diploma_shaba.activity.utils.showAuthResultDialog
import com.example.and_diploma_shaba.activity.utils.showLoginAuthDialog


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: AuthViewModel by viewModels()

    companion object {
        const val photoRequestCode = 1
    }

    @Inject
    lateinit var appAuth: AppAuth
    private var imgIri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel.authData.observe(this) {
            invalidateOptionsMenu()
        }

        with(supportFragmentManager) {
            setFragmentResultListener("keyMainFragment", this@MainActivity) { _, bundle ->
                replaceFragment(RegistrationFragment::class.java, bundle)
            }

//            setFragmentResultListener("keyEvents", this@MainActivity) { _, bundle ->
//                replaceFragment(EventsFeedFragment::class.java, bundle)
//            }
//
            setFragmentResultListener("keyWall", this@MainActivity) { _, bundle ->
                replaceFragment(PostsFragment::class.java, bundle)
            }

//            setFragmentResultListener("keyJobs", this@MainActivity) { _, bundle ->
//                replaceFragment(JobFragment::class.java, bundle)
//            }

//            setFragmentResultListener("keyNewJob", this@MainActivity) { _, bundle ->
//                replaceFragment(NewJobFragment::class.java, bundle)
//            }

            setFragmentResultListener("keyNewPost", this@MainActivity) { _, bundle ->
                replaceFragment(NewPostFragment::class.java, bundle)
            }
//            setFragmentResultListener("keyNewEvent", this@MainActivity) { _, bundle ->
//                replaceFragment(NewEventFragment::class.java, bundle)
  //          }
//            setFragmentResultListener("keyMapPicker", this@MainActivity) { _, bundle ->
//                replaceFragment(MapPickerFragment::class.java, bundle)
//            }

        }

    }

    private fun replaceFragment(
        java: Class<out Fragment>,
        bundle: Bundle
    ) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_container_view_tag, java, bundle
            )
            addToBackStack(java.simpleName)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        menu?.let {
            it.setGroupVisible(R.id.unauthenticated, !viewModel.authenticated)
           // it.setGroupVisible(R.id.authenticated, viewModel.authenticated)
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home ->{
                onBackPressed()
                true
            }

            R.id.signin -> {
                showLoginAuthDialog(Dialog.LOGIN) { login, password, _ ->
                    viewModel.selectMyPage()
                    appAuth.authUser(login, password) {
                        showAuthResultDialog(it)
                        viewModel.markMyPageAlreadyOpened()
                        Toast.makeText(this,
                        it.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    }
                }
                true
            }

            R.id.signup -> {
                showLoginAuthDialog(Dialog.REGISTER) { login, password, name ->
                    viewModel.selectMyPage()
                    appAuth.newUserRegistration(login, password, name//, imgIri?.toString()
                    ) {
                        showAuthResultDialog(it)
                        viewModel.markMyPageAlreadyOpened()
                        Toast.makeText(this,
                            it.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                true
            }

            R.id.signout -> {
                appAuth.removeAuth()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_ERROR) {
            Snackbar.make(findViewById(R.id.root), ImagePicker.getError(data), Snackbar.LENGTH_LONG).show()
            return
        }
        if (resultCode == Activity.RESULT_OK && requestCode ==  photoRequestCode ){
            data?.let { imgIri = it.data }
            return
        }
    }

}


//@AndroidEntryPoint
//
//class MainActivity : AppCompatActivity(R.layout.activity_main) {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //val binding = ActivityMainBinding.inflate(layoutInflater)
//        //setContentView(binding.root)
//        // supportFragmentManager.commit {
//        // add(R.id.nav_host_fragment, FeedFragment())
//        // }
//
//        //TODO проверить FCM
//        // TODO подключить удаленную БД
//
//        checkGoogleApiAvailability()
//
//
//
//        intent?.let {
//            if (it.action != Intent.ACTION_SEND) {
//                return@let
//            }
//
//            val text = it.getStringExtra(Intent.EXTRA_TEXT)
//            if (text?.isNotBlank() != true) {
//                return@let
//            }
//
//            intent.removeExtra(Intent.EXTRA_TEXT)
//            findNavController(R.id.nav_host_fragment)
//                .navigate(
//                    R.id.action_feedFragment_to_newPostFragment,
//                    Bundle().apply {
//                        textArg = text
//                    }
//                )
//        }
//
//
//    }
//
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////        if (resultCode == ImagePicker.RESULT_ERROR) {
////            Snackbar.make(findViewById(R.id.root), ImagePicker.getError(data), Snackbar.LENGTH_LONG).show()
////            return
////        }
////        if (resultCode == Activity.RESULT_OK && requestCode ==  photoRequestCode ){
////            data?.let { imgIri = it.data }
////            return
////        }
////    }
//
//    private fun checkGoogleApiAvailability() {
//        with(GoogleApiAvailability.getInstance()) {
//            val code = isGooglePlayServicesAvailable(this@MainActivity)
//            if (code == ConnectionResult.SUCCESS) {
//                return@with
//            }
//            if (isUserResolvableError(code)) {
//                getErrorDialog(this@MainActivity, code, 9000)?.show()
//                return
//            }
//            Toast.makeText(this@MainActivity, R.string.google_play_unavailable, Toast.LENGTH_LONG)
//                .show()
//        }
//
//        FirebaseMessaging.getInstance().token.addOnSuccessListener {
//            println(it)
//        }
//    }
//}