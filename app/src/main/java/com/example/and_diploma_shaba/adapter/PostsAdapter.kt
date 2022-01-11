package com.example.and_diploma_shaba.adapter

import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.databinding.CardPostBinding
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.viewmodel.kiloLogic


class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) : androidx.recyclerview.widget.ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            headerTextView.text = post.postAuthor
            timeTextView.text = post.postPublishedDate
            longTextView.text = post.postContent
            likeButton.text = kiloLogic(post.postLikes)
            likeButton.isChecked = post.postLikedByMe
            repostButton.text = kiloLogic(post.rePosts)
            seenNumTextView.text = kiloLogic(post.postSeen)

            if (post.video.isNullOrEmpty()) {
                videoButton.visibility = View.GONE
            } else videoButton.visibility = View.VISIBLE


            likeButton.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            repostButton.setOnClickListener {
                onInteractionListener.onRepost(post)
                onInteractionListener.onShare(post)
            }

            videoButton.setOnClickListener {
                onInteractionListener.onVideo(post)
            }


            moreButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            }
        }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}

