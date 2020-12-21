package com.android.kotlin_core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.kotlin_core.model.GetAuthGuestSessionNewResponseData
import com.android.kotlin_core.model.GetAuthGuestSessionNewRequestData
import com.android.kotlin_core.network.TaskManager
import com.android.kotlin_core.task.GetAuthGuestSessionNewTask
import com.android.kotlin_core.ui.SyntaxEditText
import com.android.kotlin_core.util.DebugLog
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var syntaxEditTextEmail: SyntaxEditText
    private lateinit var syntaxEditTextPhone: SyntaxEditText
    private lateinit var btnSend: Button
    private var isSyntaxPassEmail : Boolean = false
    private var isSyntaxPassPhone : Boolean = false
    private lateinit var getAuthGuestSessionNewTask: GetAuthGuestSessionNewTask

    private val myScope = object: CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Job()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        syntaxEditTextEmail = findViewById(R.id.syntaxEditTextEmail)
        syntaxEditTextPhone = findViewById(R.id.syntaxEditTextPhone)
        btnSend = findViewById(R.id.btnSend)
        var postData = GetAuthGuestSessionNewRequestData("d063d5beabbcd83c4b1d414a4cce47c4")
        getAuthGuestSessionNewTask = GetAuthGuestSessionNewTask(postData, object : GetAuthGuestSessionNewTask.TaskListener {
            override fun onSucceed(result: GetAuthGuestSessionNewResponseData) {
                DebugLog.d("Success")
            }

            override fun onFailed(error: String) {
                DebugLog.d("Error ${error.toString()}")
            }

        } )

        btnSend.isEnabled = true
        btnSend.setOnClickListener {
            myScope.launch {
                TaskManager(getAuthGuestSessionNewTask).startTask()
            }
        }

//        syntaxEditTextEmail.setOnSyntaxChangeListener(object : OnSyntaxChangeListener {
//            override fun onSyntaxChange(isSyntaxPass: Boolean) {
//                isSyntaxPassEmail = isSyntaxPass
//                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
//            }
//        })
//        syntaxEditTextPhone.setOnSyntaxChangeListener(object : OnSyntaxChangeListener {
//            override fun onSyntaxChange(isSyntaxPass: Boolean) {
//                isSyntaxPassPhone = isSyntaxPass
//                btnSend.isEnabled = (isSyntaxPassEmail && isSyntaxPassPhone)
//            }
//        })

    }



}


