package com.android.e_learning.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import com.android.e_learning.Model.ChatModel
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    private val sMESSAGE_TYPE_IS_SENDER = 0
    private val sMESSAGE_TYPE_IS_RECEIVER = 1
    private val currentUserId by lazy { FirebaseAuth.getInstance().currentUser?.uid!! }
    private var listOfMessages = ArrayList<ChatModel>()


    fun setChatList(list: ArrayList<ChatModel>) {
        this.listOfMessages = list
        notifyItemChanged(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (viewType == sMESSAGE_TYPE_IS_SENDER) {
            return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(com.android.e_learning.R.layout.item_chat_sender, parent, false));
        }
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(com.android.e_learning.R.layout.item_chat_receiver, parent, false));
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (listOfMessages[position].senderId == currentUserId) {
            holder.userName.text = listOfMessages[position].userName
            holder.userName.visibility = View.GONE
            holder.userMessage.text = listOfMessages[position].message
        }


        holder.userName.text = listOfMessages[position].userName
        holder.userMessage.text = listOfMessages[position].message

    }

    override fun getItemCount(): Int = if (listOfMessages.size == null) 0 else listOfMessages.size


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView = itemView.findViewById(com.android.e_learning.R.id.chat_userName_TV)
        var userMessage: TextView =
            itemView.findViewById(com.android.e_learning.R.id.chat_Message_TV)
    }


    override fun getItemViewType(position: Int): Int {
        if (listOfMessages[position].senderId == currentUserId) {
            return sMESSAGE_TYPE_IS_SENDER
        }
        return sMESSAGE_TYPE_IS_RECEIVER
    }
}