package com.josycom.mayorjay.chatpadi.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.josycom.mayorjay.chatpadi.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chat_button.setOnClickListener {
            navigateToChat()
        }
    }

    private fun navigateToChat() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val userIdRegex = "[a-zA-Z]*[0-9]{4}".toRegex()
        if (id_input_editText.text!!.matches(userIdRegex)) {
            val bundle = Bundle()
            bundle.putString("user_id", id_input_editText.text.toString())
            findNavController().navigate(R.id.action_homeFragment_to_chatFragment, bundle)
            id_input_editText.setText("")
            imm.hideSoftInputFromWindow(home_layout.windowToken, 0)
        } else {
            id_input_layout.error = "Use the correct format. E.g: Michael2356"
            imm.hideSoftInputFromWindow(home_layout.windowToken, 0)
        }
    }
}