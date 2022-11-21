package com.example.and_diploma_shaba.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.activity.utils.PagingLoadStateAdapter
import com.example.and_diploma_shaba.adapter.OnUsersInteractionListener
import com.example.and_diploma_shaba.adapter.UsersAdapter
import com.example.and_diploma_shaba.databinding.FragmentUsersBinding
import com.example.and_diploma_shaba.databinding.CardUserBinding
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.card_user.*


@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {
    private val viewModel: UsersViewModel by activityViewModels()
    private var _binding: FragmentUsersBinding? = null

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
          _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UsersAdapter(object : OnUsersInteractionListener {
            override fun onWall(user: User) {
                val bundle = Bundle()
                bundle.putLong("user", user.userId)
                setFragmentResult("keyWall", bundle)
            }

//            private fun setFragmentResult(s: String, bundle: Bundle) {
//
//            }

//            override fun onJobs(user: User) {
//                val bundle = Bundle()
//                bundle.putLong("user", user.userId)
//                setFragmentResult("keyJobs", bundle)
//
//            }

            override fun onEvents(user: User) {
                val bundle = Bundle()
                bundle.putLong("user", user.userId)
                setFragmentResult("keyEvents", bundle)
            }

        })


        _binding?.ulist?.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(adapter::retry),
                footer = PagingLoadStateAdapter(adapter::retry)
            )


        _binding?.ulist?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.HORIZONTAL
            )
        )

        val offesetH = resources.getDimensionPixelSize(R.dimen.common_spacing)
        _binding?.ulist?.addItemDecoration(
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


        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            _binding?.swiperefresh?.isRefreshing = state.refreshing
            _binding?.progress?.isVisible = state.loading && _binding?.swiperefresh?.isVisible == false

            if (state.error) {
                Snackbar.make(_binding!!.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadUsers() }
                    .show()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.cachedusers.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { states ->
                _binding?.swiperefresh?.isRefreshing = states.refresh is LoadState.Loading
                _binding?.errorOccured?.isVisible = states.refresh is LoadState.Error

                if (states.refresh.endOfPaginationReached) {
                    _binding?.emptyText?.isVisible = adapter.itemCount == 0
                }
            }
        }



        _binding?.swiperefresh?.setOnRefreshListener {
            viewModel.loadUsers()

        }





    }

}

