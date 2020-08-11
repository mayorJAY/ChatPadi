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
import com.josycom.mayorjay.chatpadi.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentHomeBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chat_button.setOnClickListener {
            navigateToChat()
        }
    }

    private fun navigateToChat() {
        val bundle = Bundle()
        val userIdRegex = "[a-zA-Z]*[0-9]{4}".toRegex()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (my_id_input_editText.text!!.matches(userIdRegex)) {
            bundle.putString("my_id", my_id_input_editText.text.toString())
        } else {
            my_id_input_layout.error = "Use the correct format. E.g: Michael2356"
            imm.hideSoftInputFromWindow(home_layout.windowToken, 0)
            return
        }

        if (their_id_input_editText.text!!.matches(userIdRegex)) {
            bundle.putString("their_id", their_id_input_editText.text.toString())
        } else {
            their_id_input_layout.error = "Use the correct format. E.g: Donald4910"
            imm.hideSoftInputFromWindow(home_layout.windowToken, 0)
            return
        }

        findNavController().navigate(R.id.action_homeFragment_to_chatFragment, bundle)
        my_id_input_editText.setText("")
        their_id_input_editText.setText("")
        imm.hideSoftInputFromWindow(home_layout.windowToken, 0)
    }
}