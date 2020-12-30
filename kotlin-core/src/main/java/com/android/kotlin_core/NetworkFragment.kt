package com.android.kotlin_core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.kotlin_core.databinding.FragmentNetworkBinding
import com.android.kotlin_core.network.ApiResult
import com.android.kotlin_core.network.TaskManager
import com.android.kotlin_core.task.GetAuthSessionTask
import com.android.kotlin_core.task.GetMoviePopularTask
import com.android.kotlin_core.ui.viewBinding
import com.android.kotlin_core.util.DebugLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NetworkFragment : Fragment(R.layout.fragment_network) {
    private val binding by viewBinding(FragmentNetworkBinding::bind)
    private var getAuthSessionTask = GetAuthSessionTask()
    private var getMoviePopularTask = GetMoviePopularTask()

    private val myScope = object: CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Job()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNewSession.setOnClickListener {
            myScope.launch {
                when(val result = TaskManager(getAuthSessionTask).startTask()) {
                    is ApiResult.Success -> {
                        DebugLog.d("${result.data}")
                    }
                    is ApiResult.Error -> {
                        DebugLog.d("${result.errorData}")
                    }
                    is ApiResult.Exception -> {
                        DebugLog.d("${result.exception}")
                    }
                }
            }
        }

        binding.btnMoviePopular.setOnClickListener {
            myScope.launch {
                when(val result = TaskManager(getMoviePopularTask).startTask()) {
                    is ApiResult.Success -> {
                        DebugLog.d("${result.data}")
                    }
                    is ApiResult.Error -> {
                        DebugLog.d("${result.errorData}")
                    }
                    is ApiResult.Exception -> {
                        DebugLog.d("${result.exception}")
                    }
                }
            }
        }

    }
}