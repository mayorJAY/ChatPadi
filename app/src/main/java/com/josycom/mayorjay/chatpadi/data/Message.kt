package com.josycom.mayorjay.chatpadi.data

import androidx.recyclerview.widget.DiffUtil
import java.util.*

data class Message(
    var message: String? = null,
    var sender_id: Int? = null,
    var created_at: String? = null
)

val messageDiffUtil = object : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.created_at == newItem.created_at
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}