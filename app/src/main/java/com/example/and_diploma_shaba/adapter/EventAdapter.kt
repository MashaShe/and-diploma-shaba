package com.example.and_diploma_shaba.adapter

import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.databinding.CardEventBinding
import com.example.and_diploma_shaba.databinding.CardPostBinding
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.viewmodel.kiloLogic


class EventAdapter(
    private val onInteractionListener: OnInteractionListener
) : androidx.recyclerview.widget.ListAdapter<Event, EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = CardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class EventViewHolder(
    private val binding: CardEventBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.apply {
            authorTextView.text = event.authorName
            timeTextView.text = event.eventDateTime
            longTextView.text = event.eventContent


            if (event.eventLink.isNullOrEmpty()) {
                linkTextView.visibility = View.GONE
            } else linkTextView.visibility = View.VISIBLE


            moreButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(event)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(event)
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

class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }

}

