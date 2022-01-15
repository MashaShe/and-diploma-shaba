package com.example.and_diploma_shaba.adapter

import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.and_diploma_shaba.R
import com.example.and_diploma_shaba.databinding.CardPostBinding
import com.example.and_diploma_shaba.databinding.FragmentRegistrationBinding
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.viewmodel.kiloLogic


class UserAdapter(
    private val onInteractionListener: OnInteractionListener
) : androidx.recyclerview.widget.ListAdapter<User, UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = FragmentRegistrationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

}

class UserViewHolder(
    private val binding: FragmentRegistrationBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.apply {

            userFirstName.text = user.userFirstName
            userLastName.text = user.userLastName
          //  editTextTextPassword.hint = user.userPass
            
            loginButton.setOnClickListener{
                onInteractionListener.onLogin(user)
            }

            regButton.setOnClickListener {
                onInteractionListener.onRegister(user)
            }
        }
    }
}



class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}
