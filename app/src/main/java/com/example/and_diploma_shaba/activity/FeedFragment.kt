package com.example.and_diploma_shaba.activity
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import com.example.and_diploma_shaba.R
//import com.example.and_diploma_shaba.adapter.OnInteractionListener
//import com.example.and_diploma_shaba.adapter.PostsAdapter
//import com.example.and_diploma_shaba.databinding.FragmentFeedBinding
//import com.example.and_diploma_shaba.dto.Post
//import com.example.and_diploma_shaba.viewmodel.PostViewModel
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//
class FeedFragment : Fragment() {
//
//    private val viewModel: PostViewModel by viewModels(
//        ownerProducer = ::requireParentFragment
//    )
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding = FragmentFeedBinding.inflate(
//            inflater,
//            container,
//            false
//        )
//
//
//        val adapter = PostsAdapter(object : OnInteractionListener {
//            override fun onLike(post: Post) {
//             //   viewModel.likeById(post.postId)
//            }
//
//            override fun onRemove(post: Post) {
//             //   viewModel.removeById(post.postId)
//            }
//
//            override fun onRepost(post: Post) {
//             //   viewModel.repostById(post.postId)
//            }
//
//
//            override fun onShare(post: Post) {
//                val myIntent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(Intent.EXTRA_TEXT, post.postContent)
//                    type = "text/plain"
//                }
//
//                val shareIntent =
//                    Intent.createChooser(myIntent, getString(R.string.chooser_share_post))
//                startActivity(shareIntent)
//            }
//
//            override fun onVideo(post: Post) {
//                if (post.video.isNullOrEmpty()) {
//                    Toast.makeText(
//                        context,
//                        "No video",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    val videoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                    startActivity(videoIntent)
//                }
//            }
//
//            override fun onEdit(post: Post) {
//                val bundle = Bundle().apply {
//                    putString("postContent", post.postContent)
//                }
//              //  viewModel.edit(post)
//                findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, bundle)
//            }
//        })
//
//        binding.postList.adapter = adapter
//        viewModel.data.observe(viewLifecycleOwner, { posts ->
//            adapter.submitList(posts)
//        })
//
//        binding.fab.setOnClickListener {
//            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
//        }
//
//        binding.goToEvent.setOnClickListener{
//            findNavController().navigate(R.id.action_feedFragment_to_eventsFeedFragment)
//        }
//
//        return binding.root
//    }
}
//
//
