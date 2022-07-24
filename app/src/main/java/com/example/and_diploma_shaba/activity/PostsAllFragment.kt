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
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.adapter.OnInteractionListener
//import com.example.and_diploma_shaba.activity.utils.PagingLoadStateAdapter
import com.example.and_diploma_shaba.adapter.PostsAdapter
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.databinding.FragmentFeedBinding
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.viewmodel.AuthViewModel
import com.example.and_diploma_shaba.viewmodel.PostAllViewModel
import javax.inject.Inject
import com.example.and_diploma_shaba.activity.utils.prepareIntent
//import com.example.and_diploma_shaba.viewmodel.MediaWorkPostViewModel


@AndroidEntryPoint
class PostsAllFragment : Fragment() {
    private val viewModel: PostAllViewModel by activityViewModels()
    //private val mwPostViewModel: MediaWorkPostViewModel by activityViewModels()
    private var _binding: FragmentFeedBinding? = null

    @Inject
    lateinit var appAuth: AppAuth

    private lateinit var adapter : PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

          adapter = PostsAdapter(object : OnInteractionListener {




//            override fun onNotLogined(post: Post) {
//                Toast.makeText(requireActivity(),
//                    getString(R.string.login_first_action),
//                    Toast.LENGTH_SHORT).show()
//            }
//
//              override fun onMediaReadyClick(post: Post) {
//                  mwPostViewModel.openMedia(post.id){ file ->
//                      startActivity(Intent.createChooser(prepareIntent(file),
//                          getString(R.string.choose_app)))
//
//                  }
//              }

//              override fun onMediaPrepareClick(post: Post) {
//                  mwPostViewModel.downloadMedia(post)
//            }

//            override fun onPlaceClick(post: Post) {
//                val intent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("geo:<" + post.coords?.latitude.toString() +
//                            ">,<" + post.coords?.longitude.toString() +
//                            ">?q=<" + post.coords?.latitude.toString() +
//                            ">,<" + post.coords?.longitude.toString() +
//                            ">(" + getString(R.string.place) +
//                            ")")
//                )
//                startActivity(intent)
//            }


            override fun onLike(post: Post) {
                    viewModel.like(post)
            }


            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.postContent)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, getString(R.string.chooser_share_post)))
            }
        })


//        _binding?.postList?.adapter = adapter.withLoadStateHeaderAndFooter(
//            header = PagingLoadStateAdapter(adapter::retry),
//            footer = PagingLoadStateAdapter(adapter::retry)
//        )


        val offesetH = resources.getDimensionPixelSize(R.dimen.common_spacing)
        _binding?.postList?.addItemDecoration(
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


        viewModel.dataState.observe(viewLifecycleOwner) {// state ->
//            _binding?.swiperefresh?.isRefreshing = state.refreshing
//            _binding?.progress?.isVisible = state.loading && _binding?.swiperefresh?.isVisible == false
//            if (state.error) {
//                Snackbar.make(_binding!!.root, R.string.error_loading, Snackbar.LENGTH_LONG)
//                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
//                    .show()
//            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.feedModels.collectLatest {
                adapter.submitData(it)

            }
        }

        lifecycleScope.launchWhenCreated {
//             adapter.loadStateFlow.collectLatest { states ->
//                  _binding?.swiperefresh?.isRefreshing = states.refresh is LoadState.Loading
//                 _binding?.errorOccured?.isVisible = states.refresh is LoadState.Error
//
//                 if (states.refresh.endOfPaginationReached) {
//                     _binding?.emptyText?.isVisible = adapter.itemCount == 0
//                 }
             //}
         }


//        _binding?.swiperefresh?.setOnRefreshListener {
//            viewModel.refreshPosts()
//        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
