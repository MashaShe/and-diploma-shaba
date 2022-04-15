package com.example.and_diploma_shaba.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.adapter.EventAdapter
import com.example.and_diploma_shaba.adapter.OnInteractionListener
import com.example.and_diploma_shaba.adapter.PostsAdapter
import com.example.and_diploma_shaba.databinding.FragmentEventsFeedBinding
import com.example.and_diploma_shaba.databinding.FragmentFeedBinding
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.viewmodel.EventViewModel
import com.example.and_diploma_shaba.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class EventsFeedFragment : Fragment() {
    private val viewModel: EventViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventsFeedBinding.inflate(
            inflater,
            container,
            false
        )


        val adapter = EventAdapter(object : OnInteractionListener {

            override fun onRemove(event: Event) {
                viewModel.removeById(event.eventId)
            }


            override fun onEdit(event: Event) {
                val bundle = Bundle().apply {
                    putString("eventContent", event.eventContent)
                }
                viewModel.edit(event)
                findNavController().navigate(R.id.action_eventsFeedFragment_to_editEventFragment, bundle)
            }
        })

        binding.eventList.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { events ->
            adapter.submitList(events)
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_eventsFeedFragment_to_newEventFragment)
}

        binding.goToPost.setOnClickListener {
            findNavController().navigate(R.id.action_eventsFeedFragment_to_feedFragment)
        }

        return binding.root
    }
}