package com.amsolutions.mychatapplication.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amsolutions.mychatapplication.R
import com.amsolutions.mychatapplication.data.model.User

class UserSelectionAdapter ()
    : ListAdapter<User, UserSelectionAdapter.UserSelectionHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSelectionHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item, parent,
            false)
        return UserSelectionHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserSelectionHolder, position: Int) {
        with(getItem(position)) {
            holder.tvName.text = name
            holder.tvMobileNumber.text = email
            if(isSelected){
                holder.userSelected.visibility = View.VISIBLE
            }else{
                holder.userSelected.visibility = View.GONE
            }

            holder.itemView.setOnClickListener{
                if(isSelected){
                    isSelected = false
                    holder.userSelected.visibility = View.GONE
                }else{
                    isSelected = true
                    holder.userSelected.visibility = View.VISIBLE
                }
            }
        }
    }

    fun getUserAt(position: Int) = getItem(position)


    inner class UserSelectionHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val tvName: TextView = itemView.findViewById(R.id.text_view_name)
        val tvMobileNumber: TextView = itemView.findViewById(R.id.text_view_mobileNum)
        val userSelected: CheckBox = itemView.findViewById(R.id.selectedUser)

    }
}

private val diffCallback = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem.name == newItem.name
                && oldItem.email == newItem.email
}