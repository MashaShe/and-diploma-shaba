package com.example.and_diploma_shaba.activity.utils

import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener

import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.activity.MainActivity
import com.example.and_diploma_shaba.repository.AppNetState
import com.google.android.datatransport.runtime.backends.BackendResponse.ok


enum class Dialog {
    LOGIN, REGISTER, REGISTER_AVATAR
}



fun Activity.showLoginAuthDialog(
    dialogType: Dialog,
    actionCallback: (login: String, password: String, name: String) -> Unit) {

    val dialogBuilder = MaterialAlertDialogBuilder(this)
    val dialogView = layoutInflater.inflate(R.layout.fragment_registration, null)

    dialogBuilder.setCancelable(false)
    dialogBuilder.setView(dialogView)
        .setPositiveButton(getString(R.string.ok)) { a, b ->
            val login = dialogView.findViewById<EditText>(R.id.editTextTextEmailAddress)
            val password = dialogView.findViewById<EditText>(R.id.editTextTextPassword)
            val name = dialogView.findViewById<EditText>(R.id.editTextTextName)

            actionCallback(login.text.toString(), password.text.toString(), name.text.toString())
        }
        .setNegativeButton(getString(R.string.cancel)) { a, b ->

        }

    when (dialogType) {
        Dialog.LOGIN -> {
            dialogBuilder.setTitle(R.string.log_in_button_text)
        }

        Dialog.REGISTER -> {
            dialogBuilder.setTitle(R.string.reg_button_text)

        }

        Dialog.REGISTER_AVATAR -> {
            dialogBuilder.setTitle(R.string.reg_button_text)
        }
    }

    //TODO  НЕ ПРИВЗЯАН XML К активити, тк у меня фрагмент и плюс потерялись какие-то значение - см ошибки внизу

    val alertDialog = dialogBuilder.create()

    when (dialogType) {
        Dialog.REGISTER -> {
            showRegFields(dialogView, alertDialog, this)
        }

        Dialog.REGISTER_AVATAR -> {
            showRegFields(dialogView, alertDialog, this)
        }
    }

    alertDialog.show()

    when (dialogType) {
        Dialog.REGISTER, Dialog.REGISTER_AVATAR -> {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                true
        }
    }


}

private fun showRegFields(
    dialogView: View,
    alertDialog: AlertDialog,
    activity: Activity
) {

    val password = dialogView.findViewById<EditText>(R.id.editTextTextPassword)

 //   val passwordL = dialogView.findViewById<TextView>(R.id.passwordL2)
  //  val password2 = dialogView.findViewById<EditText>(R.id.password2)
  //  val nameL = dialogView.findViewById<TextView>(R.id.nameL)
    val name = dialogView.findViewById<EditText>(R.id.editTextTextName)
  //  val setAva = dialogView.findViewById<MaterialButton>(R.id.setAva)

    password.visibility = View.VISIBLE
  //  passwordL.visibility = View.VISIBLE
//    password2.visibility = View.VISIBLE
   name.visibility = View.VISIBLE
 //   nameL.visibility = View.VISIBLE
  //  setAva.visibility = View.VISIBLE
//
//    setAva.setOnClickListener {
//        ImagePicker.with(activity)
//            .crop()
//            .compress(1024)
//            .galleryOnly()
//            .galleryMimeTypes(
//                arrayOf(
//                    "image/png",
//                    "image/jpeg",
//                )
//            )
//            .start(MainActivity.photoRequestCode)
//    }

//    password.addTextChangedListener {
//        checkField(activity,password, password2, alertDialog)
//    }
//
//    password2.addTextChangedListener {
//        checkField(activity,password2, password, alertDialog)
//    }
}

//private fun checkField(
//    activity: Activity,
//    password: EditText,
//    password2: EditText,
//    alertDialog: AlertDialog
//) {
//    if (password.text.toString() == password2.text.toString()) {
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
//            true
//        password.error = null
//        password2.error = null
//
//    } else {
//        password.error = activity.getString(R.string.Passwords_do_not_match)
//        password2.error = null
//        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
//            false
//    }
//}

fun Activity.showAuthResultDialog(message: AppNetState) {
    runOnUiThread {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.enter_dialog)
            .setPositiveButton(android.R.string.ok) { dialog, which -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
        when (message) {
            AppNetState.NO_INTERNET -> dialog.setMessage(R.string.error_dialog_auth_internet)
            AppNetState.NO_SERVER_CONNECTION -> dialog.setMessage(R.string.error_dialog_auth_server)
            AppNetState.CONNECTION_ESTABLISHED -> dialog.setMessage(R.string.error_dialog_auth_success)
            AppNetState.THIS_USER_NOT_REGISTERED -> dialog.setMessage(R.string.error_dialog_auth_nouser)
            AppNetState.INCORRECT_PASSWORD -> dialog.setMessage(R.string.error_dialog_auth_incpass)
            AppNetState.SERVER_ERROR_500 -> dialog.setMessage(R.string.error_dialog_auth_server_error)
        }
        dialog.show()
    }
}