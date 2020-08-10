package com.josycom.mayorjay.chatpadi.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josycom.mayorjay.chatpadi.R
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    //private val viewModel: ChatViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id_text.text = arguments?.getString("user_id")
    }

}