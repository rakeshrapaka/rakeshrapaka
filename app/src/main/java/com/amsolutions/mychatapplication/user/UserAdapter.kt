package com.amsolutions.mychatapplication.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amsolutions.mychatapplication.R
import com.amsolutions.mychatapplication.data.model.User

class UserAdapter (private val onItemClickListener: (User) -> Unit)
    : ListAdapter<User, UserAdapter.UserHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item, parent,
            false)
        return UserHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        with(getItem(position)) {
            holder.tvName.text = name
            holder.tvMobileNumber.text = mobileNum.toString()
        }
    }

    fun getUserAt(position: Int) = getItem(position)


    inner class UserHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val tvName: TextView = itemView.findViewById(R.id.text_view_name)
        val tvMobileNumber: TextView = itemView.findViewById(R.id.text_view_mobileNum)

        init {
            itemView.setOnClickListener {
                if(adapterPosition != NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }

    }
}

private val diffCallback = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem.name == newItem.name
                && oldItem.mobileNum == newItem.mobileNum
}