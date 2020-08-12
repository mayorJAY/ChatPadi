package com.josycom.mayorjay.chatpadi.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.josycom.mayorjay.chatpadi.R
import com.josycom.mayorjay.chatpadi.adapter.MessageViewHolder
import com.josycom.mayorjay.chatpadi.adapter.RecyclerViewAdapter
import com.josycom.mayorjay.chatpadi.adapter.ViewHolder
import com.josycom.mayorjay.chatpadi.data.Message
import com.josycom.mayorjay.chatpadi.data.messageDiffUtil
import com.josycom.mayorjay.chatpadi.databinding.FragmentChatBinding
import com.josycom.mayorjay.chatpadi.mqtt.MqttClientHelper
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by MayorJay
 * This Fragment houses the Chat between users
 * It only draws views, displays data to the UI and handles
 * the chat service in relation to the fragment lifecycle
 * The main Logic is housed in the #ChatViewModel
 */
class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModel()
    private var msgList: MutableList<Message> = mutableListOf()
    private lateinit var myTopic: String
    private lateinit var theirTopic: String
    companion object {
        const val TAG = "ChatFragment"
    }

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
        myTopic = "$myId/ChatPadiTopic"
        theirTopic = "$theirId/ChatPadiTopic"
        viewModel.myTopic = myTopic
        viewModel.theirTopic = theirTopic

        viewModel.setCallBack()
        viewModel.setTopic()

        send_button.setOnClickListener {
            if (msg_input_editText.text.isNullOrEmpty()) {
                msg_input_layout.error = "Please type a message"
            }

            if (viewModel.isConnected()) {
                publish()
                msg_input_editText.setText("")
            } else {
                Log.d(TAG, "Can't publish")
            }
        }
        setUpAdapter()
        observeMsg()
    }

    private fun setUpAdapter() {
        val messageAdapter = object: RecyclerViewAdapter<Message>(messageDiffUtil) {
            override fun getLayoutRes(model: Message): Int {
                return if (model.message_topic!! == theirTopic) R.layout.item_message_incoming
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
            hasFixedSize()
        }
        messageAdapter.submitList(msgList)
    }

    private fun observeMsg() {
        viewModel.msgLiveData.observe(viewLifecycleOwner, Observer { msg ->
            msgList.add(msg)
        })
    }

    private fun publish() {
        viewModel.publish(myTopic, getMessage())
    }

    private fun getMessage() = msg_input_editText.text.toString().trim()

    override fun onResume() {
        super.onResume()
        viewModel.register()
    }

    override fun onPause() {
        viewModel.unregister()
        super.onPause()
    }

    override fun onDestroy() {
        //viewModel.unSubscribe()
        viewModel.disconnect()
        super.onDestroy()
    }
}