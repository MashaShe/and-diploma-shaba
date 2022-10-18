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
import com.example.and_diploma_shaba.databinding.FragmentNewEventBinding
import com.example.and_diploma_shaba.databinding.FragmentNewPostBinding
import com.example.and_diploma_shaba.util.AndroidUtils
import com.example.and_diploma_shaba.util.StringArg
//import com.example.and_diploma_shaba.viewmodel.EventViewModel
import com.example.and_diploma_shaba.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class NewEventFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

//    private val viewModel: EventViewModel by viewModels(
//        ownerProducer = ::requireParentFragment
//    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewEventBinding.inflate(
            inflater,
            container,
            false
        )
        arguments?.textArg
            ?.let(binding.edit::setText)

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
               // viewModel.changeContent(content)
               // viewModel.save()
                AndroidUtils.hideKeyboard(requireView())
                //findNavController().navigateUp()
            }
        }
        return binding.root
    }
}