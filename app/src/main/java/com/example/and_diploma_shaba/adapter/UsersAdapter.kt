package com.example.and_diploma_shaba.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.and_diploma_shaba.BuildConfig
import com.example.and_diploma_shaba.R
//import com.example.and_diploma_shaba.adapter.loadX
import com.example.and_diploma_shaba.databinding.CardUserBinding
import com.example.and_diploma_shaba.dto.User
//import com.example.and_diploma_shaba.view.loadCircleCrop

interface OnUsersInteractionListener {
    fun onWall(post: User) {}
    fun onJobs(post: User) {}
    fun onEvents(post: User) {}
}

class UsersAdapter(
    private val onUsersInteractionListener: OnUsersInteractionListener,
) : PagingDataAdapter<User, RecyclerView.ViewHolder>(PostDiffCallbackUser()) {


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is User -> R.layout.card_user
            else -> error("unsupported type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            R.layout.card_user -> {
                val binding =
                    CardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return UserViewHolder(binding, onUsersInteractionListener)
            }


            else -> error("no such viewholder")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val item = getItem(position)
                if (item != null) {
                    holder.bind(item)
                } else {
                    Log.e("exc", "users onBindViewHolder error")
                }
            }

        }
    }
}


class UserViewHolder(
    private val binding: CardUserBinding,
    private val onInteractionListener: OnUsersInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.apply {
            userInfo.text = user.userLastName
            userid.text = "#${user.userId}"
//            avatar.loadX(
//                user.avatar,
//                RequestOptions()
//                    .transform(CircleCrop())
//                    .placeholder(R.drawable.ic_baseline_user_placeholder)
//                    .error(R.drawable.ic_baseline_error_placeholder)
//                    .timeout(7_000)
//            )


            toEvents.setOnClickListener {
                onInteractionListener.onEvents(user)
            }

            toWall.setOnClickListener {
                onInteractionListener.onWall(user)
            }

            toJobs.setOnClickListener {
                onInteractionListener.onJobs(user)
            }
        }
    }
}

class PostDiffCallbackUser : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}




//package com.example.and_diploma_shaba.adapter
//
//import androidx.recyclerview.widget.DiffUtil
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.PopupMenu
//import androidx.recyclerview.widget.RecyclerView
//import com.example.and_diploma_shaba.R
//import com.example.and_diploma_shaba.databinding.CardPostBinding
//import com.example.and_diploma_shaba.databinding.FragmentRegistrationBinding
//import com.example.and_diploma_shaba.dto.Post
//import com.example.and_diploma_shaba.dto.User
//import com.example.and_diploma_shaba.viewmodel.kiloLogic
//
//
//class UserAdapter(
//    private val onInteractionListener: OnInteractionListener
//) : androidx.recyclerview.widget.ListAdapter<User, UserViewHolder>(UserDiffCallback()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//        val binding = FragmentRegistrationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return UserViewHolder(binding, onInteractionListener)
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val user = getItem(position)
//        holder.bind(user)
//    }
//
//}
//
//class UserViewHolder(
//    private val binding: FragmentRegistrationBinding,
//    private val onInteractionListener: OnInteractionListener
//) : RecyclerView.ViewHolder(binding.root) {
//    fun bind(user: User) {
//        binding.apply {
//
//            userFirstName.text = user.userFirstName
//            userLastName.text = user.userLastName
//          //  editTextTextPassword.hint = user.userPass
//
////            loginButton.setOnClickListener{
////                onInteractionListener.onLogin(user)
////            }
////
////            regButton.setOnClickListener {
////                onInteractionListener.onRegister(user)
////            }
//        }
//    }
//}
//
//
//
//class UserDiffCallback : DiffUtil.ItemCallback<User>() {
//    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
//        return oldItem.userId == newItem.userId
//    }
//
//    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
//        return oldItem == newItem
//    }
//
//}
