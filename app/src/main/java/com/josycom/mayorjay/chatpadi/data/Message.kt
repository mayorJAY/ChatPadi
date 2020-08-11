package com.josycom.mayorjay.chatpadi.data

import androidx.recyclerview.widget.DiffUtil
import com.josycom.mayorjay.chatpadi.utils.convertTime
import java.util.*

data class Message(
    var message_topic: String? = null,
    var message: String? = null,
    var created_at: String? = Date(System.currentTimeMillis()).convertTime()
)

val messageDiffUtil = object : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.message == newItem.message
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}