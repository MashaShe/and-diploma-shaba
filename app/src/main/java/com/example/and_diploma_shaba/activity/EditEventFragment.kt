package com.example.and_diploma_shaba.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.databinding.FragmentEditEventBinding
import com.example.and_diploma_shaba.databinding.FragmentEditPostBinding
import com.example.and_diploma_shaba.util.AndroidUtils
import com.example.and_diploma_shaba.util.StringArg
//import com.example.and_diploma_shaba.viewmodel.EventViewModel
import com.example.and_diploma_shaba.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class EditEventFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

//    private val viewModel: EventViewModel by viewModels(
//        ownerProducer = ::requireParentFragment
//    )

    //TODO сделать кнопку отменить для редактирования и создания поста
    // TODO проверить как убирается клавиатура и убрать - см комменты в конце

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditEventBinding.inflate(
            inflater,
            container,
            false
        )

        val editableContent = arguments?.getString("eventContent")
        binding.edit.setText(editableContent)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {

            if (binding.edit.text.isNullOrBlank()) {
                Toast.makeText(
                    getActivity(),
                    getString(R.string.error_empty_content),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            } else {

                val content = binding.edit.text.toString()
                //viewModel.changeContent(content)
               // viewModel.save()
                AndroidUtils.hideKeyboard(requireView())
                //findNavController().navigateUp()
            }
        }
        return binding.root
    }
}


//        binding.saveButton.setOnClickListener {
//            with(binding.postContent) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_content),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                binding.group.visibility = View.GONE
//            }
//        }

//        binding.cancelButton.setOnClickListener {
//            with(binding.postContent) {
//                setText("")
//                viewModel.cancel()
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                binding.group.visibility = View.GONE
//            }
//        }


