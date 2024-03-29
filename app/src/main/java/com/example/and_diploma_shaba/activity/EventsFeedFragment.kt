package com.example.and_diploma_shaba.activity

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.activity.utils.PagingLoadStateAdapter
import com.example.and_diploma_shaba.activity.utils.prepareIntent
import com.example.and_diploma_shaba.adapter.EventsAdapter
import com.example.and_diploma_shaba.adapter.OnEventsInteractionListener
import com.example.and_diploma_shaba.databinding.FragmentEventsFeedBinding
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.viewmodel.EditEventViewModel
import com.example.and_diploma_shaba.viewmodel.EventAllViewModel
import com.example.and_diploma_shaba.viewmodel.MediaWorkEventViewModel

class EventsFeedFragment : Fragment() {
    private val viewModel: EventAllViewModel by activityViewModels()
    private val edViewModel: EditEventViewModel by activityViewModels()
    private val mwViewModel: MediaWorkEventViewModel by activityViewModels()
    private var _binding: FragmentEventsFeedBinding? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsFeedBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EventsAdapter(object : OnEventsInteractionListener {
            override fun onLinkClick(event: Event) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(event.link)
                startActivity(i)
            }

            override fun onMediaPrepareClick(event: Event) {
                mwViewModel.downloadMedia(event)
            }

            override fun onMediaReadyClick(event: Event) {
                mwViewModel.openMedia(event.id){ file ->
                    startActivity(
                        Intent.createChooser(prepareIntent(file),
                            getString(R.string.choose_app)))

                }
            }

            override fun onLike(event: Event) {
                edViewModel.setLikeOrDislike(event)
            }


            override fun onShare(event: Event) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, event.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

            }

            override fun notLogined(event: Event) {
                Toast.makeText(requireActivity(),
                    getString(R.string.login_first_action),
                    Toast.LENGTH_SHORT).show()
            }

            override fun participate(event: Event) {
                edViewModel.participate(event)
            }
        })


        _binding?.eventList?.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(adapter::retry),
                footer = PagingLoadStateAdapter(adapter::retry)
            )


        (_binding?.eventList?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        val offesetH = resources.getDimensionPixelSize(R.dimen.common_spacing)
        _binding?.eventList?.addItemDecoration(
            object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    itemPosition: Int,
                    parent: RecyclerView
                ) {
                    outRect.left += offesetH
                    outRect.right += offesetH
                }
            }
        )
        edViewModel.dataState.observe(viewLifecycleOwner) { state ->
            if (state.error) {
                Snackbar.make(_binding!!.root, R.string.login_first, Snackbar.LENGTH_LONG)
                    .show()
            }
        }


        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            _binding?.swiperefresh?.isRefreshing = state.refreshing
            _binding?.progress?.isVisible = state.loading && _binding?.swiperefresh?.isVisible == false

            if (!state.refreshing && !state.loading && !state.empty) {
                _binding?.eventList?.visibility = View.VISIBLE
            } else {
                _binding?.eventList?.visibility = View.INVISIBLE
            }
            if (state.error) {
                Snackbar.make(_binding!!.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadEvents() }
                    .show()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.events().collectLatest {
                adapter.submitData(it)

            }
        }


        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { states ->
                _binding?.swiperefresh?.isRefreshing = states.refresh is LoadState.Loading
               // _binding?.errorOccured?.isVisible = states.refresh is LoadState.Error

                if (states.refresh.endOfPaginationReached) {
                    _binding?.EventTextView?.isVisible = adapter.itemCount == 0
                }
            }
        }

        _binding?.swiperefresh?.setOnRefreshListener {
            viewModel.loadEvents()
        }

        _binding?.fabEvents?.setOnClickListener {
            setFragmentResult("keyNewEvent", Bundle())

        }

    }
}
