package com.josycom.mayorjay.chatpadi.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.josycom.mayorjay.chatpadi.R
import com.josycom.mayorjay.chatpadi.adapter.MessageViewHolder
import com.josycom.mayorjay.chatpadi.adapter.RecyclerViewAdapter
import com.josycom.mayorjay.chatpadi.adapter.ViewHolder
import com.josycom.mayorjay.chatpadi.data.Message
import com.josycom.mayorjay.chatpadi.data.messageDiffUtil
import com.josycom.mayorjay.chatpadi.databinding.FragmentChatBinding
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    //private val viewModel: ChatViewModel by viewModel()
    private lateinit var msgList: MutableList<Message>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentChatBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myId = arguments?.getString("my_id")
        val theirId = arguments?.getString("their_id")
        Toast.makeText(requireContext(), "Chat btw $myId & $theirId", Toast.LENGTH_LONG).show()

        setUpData()

        val messageAdapter = object: RecyclerViewAdapter<Message>(messageDiffUtil) {
            override fun getLayoutRes(model: Message): Int {
                return if (model.sender_id!! % 2 == 0) R.layout.item_message_incoming
                else R.layout.item_message_outgoing
            }

            override fun getViewHolder(
                view: View,
                recyclerViewAdapter: RecyclerViewAdapter<Message>
            ): ViewHolder<Message> {
                return MessageViewHolder(view)
            }
        }

        chat_recyclerview.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        messageAdapter.submitList(msgList)
    }

    private fun setUpData() {
        val msg1 = Message("Lorem Ipsum is simply dummy text of the printing and typesetting industry", 1, System.currentTimeMillis().toString())
        val msg2 = Message("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s", 2, System.currentTimeMillis().toString())
        val msg3 = Message("when an unknown printer took a galley of type and scrambled it to make a type specimen book", 3, System.currentTimeMillis().toString())
        val msg4 = Message("It has survived not only five centuries, but also the leap into electronic typesetting", 4, System.currentTimeMillis().toString())
        val msg5 = Message("remaining essentially unchanged. It was popularised in the 1960s with the", 5, System.currentTimeMillis().toString())
        val msg6 = Message("release of Letraset sheets containing Lorem Ipsum passages", 6, System.currentTimeMillis().toString())
        val msg7 = Message("and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum", 7, System.currentTimeMillis().toString())
        val msg8 = Message("It is a long established fact that a reader will be distracted by the readable content", 8, System.currentTimeMillis().toString())
        val msg9 = Message("readable content of a page when looking at its layout", 9, System.currentTimeMillis().toString())
        val msg10 = Message("The point of using Lorem Ipsum is that it has a more-or-less", 10, System.currentTimeMillis().toString())
        val msg11 = Message("that it has a more-or-less normal distribution of letters", 11, System.currentTimeMillis().toString())
        val msg12 = Message("as opposed to using 'Content here, content here', making it look like readable English", 12, System.currentTimeMillis().toString())
        val msg13 = Message("established fact that a reader will be distracted by the readable content of a page", 13, System.currentTimeMillis().toString())
        val msg14 = Message("Many desktop publishing packages and web page editors now use", 14, System.currentTimeMillis().toString())
        val msg15 = Message("Lorem Ipsum as their default model text, and a search for 'lorem ipsum'", 15, System.currentTimeMillis().toString())
        val msg16 = Message("will uncover many web sites still in their infancy", 16, System.currentTimeMillis().toString())
        val msg17 = Message("Various versions have evolved over the years, sometimes by accident", 17, System.currentTimeMillis().toString())
        msgList = mutableListOf(msg1, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15, msg16, msg17)
    }
}

