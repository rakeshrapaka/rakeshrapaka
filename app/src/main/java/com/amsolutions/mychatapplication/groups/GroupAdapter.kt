package com.amsolutions.mychatapplication.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amsolutions.mychatapplication.R
import com.amsolutions.mychatapplication.data.model.Groups
import com.amsolutions.mychatapplication.data.model.User

class GroupAdapter (private val onItemClickListener: (Groups) -> Unit)
    : ListAdapter<Groups, GroupAdapter.UserHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.group_item, parent,
            false)
        return UserHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        with(getItem(position)) {
            holder.tvName.text = groupName
        }
    }

    fun getGroupAt(position: Int) = getItem(position)


    inner class UserHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val tvName: TextView = itemView.findViewById(R.id.text_view_name)

        init {
            itemView.setOnClickListener {
                if(adapterPosition != NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }

    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Groups>() {
    override fun areItemsTheSame(oldItem: Groups, newItem: Groups) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Groups, newItem: Groups) =
        oldItem.groupName == newItem.groupName

}