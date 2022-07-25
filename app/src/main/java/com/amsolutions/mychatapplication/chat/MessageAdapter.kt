package com.amsolutions.mychatapplication.chat


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amsolutions.mychatapplication.R
import com.amsolutions.mychatapplication.data.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val userId:String) : ListAdapter<Message, RecyclerView.ViewHolder>(diffCallback) {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.recieved_message, parent,
                false)
            return MessageAdapter.ReceiveViewHolder(itemView)
        }else{
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.sent_message, parent,
                false)
            return MessageAdapter.SentViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(getItem(position)) {
            if(holder.javaClass == MessageAdapter.SentViewHolder::class.java){
                val viewHolder = holder as MessageAdapter.SentViewHolder
                viewHolder.sentMessage.text = message
            }else{
                val viewHolder = holder as MessageAdapter.ReceiveViewHolder
                viewHolder.receivedMessage.text = message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = getItem(position)
        return if(userId.equals(currentMessage.senderId)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }


    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.sentMessage)
    }

    class ReceiveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.receivedMessage)
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Message, newItem: Message) =
        oldItem.message == newItem.message
}