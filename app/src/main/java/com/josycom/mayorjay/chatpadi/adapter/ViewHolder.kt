package com.josycom.mayorjay.chatpadi.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.josycom.mayorjay.chatpadi.R
import com.josycom.mayorjay.chatpadi.data.Message
import kotlinx.android.synthetic.main.item_message_incoming.view.*
import kotlinx.android.synthetic.main.item_message_outgoing.view.*

/**
 * Created by MayorJay
 * A generic RecyclerView ViewHolder
 */
abstract class ViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(element: T)
}

/**
 * ViewHolder class for the chat messages
 */
class MessageViewHolder(itemView: View) : ViewHolder<Message>(itemView) {

    override fun bind(element: Message) {
        when(itemViewType) {
            R.layout.item_message_outgoing -> {
                with(itemView) {
                    tv_message_outgoing.text = element.message
                    tv_message_outgoing_time.text = element.created_at
                }
            }
            R.layout.item_message_incoming -> {
                with(itemView) {
                    tv_message_incoming.text = element.message
                    tv_message_incoming_time.text = element.created_at
                }
            }
        }
    }
}