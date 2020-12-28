package com.android.kotlin_core

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.kotlin_core.ui.SyntaxChangeListener
import com.android.kotlin_core.ui.SyntaxEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SyntaxEditTextFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SyntaxEditTextFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private var isSyntaxPassEmail : Boolean = false
    private var isSyntaxPassPhone : Boolean = false
    private lateinit var syntaxEditTextEmail: SyntaxEditText
    private lateinit var syntaxEditTextPhone: SyntaxEditText
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_syntax_edit_text, container, false)
        syntaxEditTextEmail = view.findViewById(R.id.syntaxEditTextEmail)
        syntaxEditTextPhone = view.findViewById(R.id.syntaxEditTextPhone)
        btnSend = view.findViewById(R.id.btnSend)
        syntaxEditTextEmail.setOnSyntaxChangeListener(object : SyntaxChangeListener {
            override fun onSyntaxChange(isSyntaxPass: Boolean) {
                isSyntaxPassEmail = isSyntaxPass
                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
            }
        })
        syntaxEditTextPhone.setOnSyntaxChangeListener(object : SyntaxChangeListener {
            override fun onSyntaxChange(isSyntaxPass: Boolean) {
                isSyntaxPassPhone = isSyntaxPass
                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
            }
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SyntaxEditTextFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SyntaxEditTextFragment().apply {
                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}