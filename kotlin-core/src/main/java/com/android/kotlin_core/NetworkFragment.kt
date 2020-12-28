package com.android.kotlin_core

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.kotlin_core.network.ApiResult
import com.android.kotlin_core.network.TaskManager
import com.android.kotlin_core.task.GetAuthSessionTask
import com.android.kotlin_core.task.GetMoviePopularTask
import com.android.kotlin_core.util.DebugLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NetworkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NetworkFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private lateinit var btnNewSession: Button
    private lateinit var btnMoviePopular: Button
    private var getAuthSessionTask = GetAuthSessionTask()
    private var getMoviePopularTask = GetMoviePopularTask()

    private val myScope = object: CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Job()
    }

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
        val view = inflater.inflate(R.layout.fragment_network, container, false)
        btnNewSession = view.findViewById(R.id.btnNewSession)
        btnMoviePopular = view.findViewById(R.id.btnMoviePopular)
        btnNewSession.setOnClickListener {
            myScope.launch {
                when(val result = TaskManager(getAuthSessionTask).startTask()) {
                    is ApiResult.Success -> {
                        DebugLog.d("${result.data}")
                    }
                    is ApiResult.Error -> {
                        DebugLog.d("${result.statusCode}")
                    }
                    is ApiResult.Exception -> {
                        DebugLog.d("${result.exception}")
                    }
                }
            }
        }

        btnMoviePopular.setOnClickListener {
            myScope.launch {
                when(val result = TaskManager(getMoviePopularTask).startTask()) {
                    is ApiResult.Success -> {
                        DebugLog.d("${result.data}")
                    }
                    is ApiResult.Error -> {
                        DebugLog.d("${result.statusCode}")
                    }
                    is ApiResult.Exception -> {
                        DebugLog.d("${result.exception}")
                    }
                }
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NetworkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                NetworkFragment().apply {
                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}