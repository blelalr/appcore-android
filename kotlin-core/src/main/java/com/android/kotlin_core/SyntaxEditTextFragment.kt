package com.android.kotlin_core

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.kotlin_core.databinding.FragmentSyntaxEditTextBinding
import com.android.kotlin_core.ui.SyntaxChangeListener
import com.android.kotlin_core.ui.viewBinding


class SyntaxEditTextFragment : Fragment() {
    private val binding by viewBinding(FragmentSyntaxEditTextBinding::bind)
    private var isSyntaxPassEmail : Boolean = false
    private var isSyntaxPassPhone : Boolean = false
    private lateinit var btnSend: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_syntax_edit_text, container, false)
        btnSend = view.findViewById(R.id.btnSend)
        binding.syntaxEditTextEmail.setOnSyntaxChangeListener(object : SyntaxChangeListener {
            override fun onSyntaxChange(isSyntaxPass: Boolean) {
                isSyntaxPassEmail = isSyntaxPass
                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
            }
        })
        binding.syntaxEditTextPhone.setOnSyntaxChangeListener(object : SyntaxChangeListener {
            override fun onSyntaxChange(isSyntaxPass: Boolean) {
                isSyntaxPassPhone = isSyntaxPass
                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
            }
        })
        return view
    }
}