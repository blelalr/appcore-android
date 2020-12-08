package com.android.kotlin_core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.kotlin_core.ui.OnSyntaxChangeListener
import com.android.kotlin_core.ui.SyntaxEditText

class MainActivity : AppCompatActivity() {
    private lateinit var syntaxEditTextEmail: SyntaxEditText
    private lateinit var syntaxEditTextPhone: SyntaxEditText
    private lateinit var btnSend: Button
    private var isSyntaxPassEmail : Boolean = false
    private var isSyntaxPassPhone : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        syntaxEditTextEmail = findViewById(R.id.syntaxEditTextEmail)
        syntaxEditTextPhone = findViewById(R.id.syntaxEditTextPhone)
        btnSend = findViewById(R.id.btnSend)
        syntaxEditTextEmail.setOnSyntaxChangeListener(object : OnSyntaxChangeListener {
            override fun onSyntaxChange(isSyntaxPass: Boolean) {
                isSyntaxPassEmail = isSyntaxPass
                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
            }
        })
        syntaxEditTextPhone.setOnSyntaxChangeListener(object : OnSyntaxChangeListener {
            override fun onSyntaxChange(isSyntaxPass: Boolean) {
                isSyntaxPassPhone = isSyntaxPass
                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
            }
        })

    }

}


