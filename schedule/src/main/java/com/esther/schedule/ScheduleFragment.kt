package com.esther.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_WEEK_DAY = "weekDay"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {
    private lateinit var rvScheduleTime: RecyclerView
    private lateinit var tvDay: TextView
    private val scheduleTimes = arrayListOf("09:00", "09:30","10:00", "10:30", "11:00", "11:30", "13:00", "13:30","14:00", "14:30","15:00", "15:30",
            "16:00", "16:30","17:00", "17:30","18:00", "18:30","19:00", "19:30","20:00", "20:30","21:00", "21:30","22:00", "22:30","23:00", "23:30")

    private var weekDay: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weekDay = it.getString(ARG_WEEK_DAY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        tvDay = view.findViewById(R.id.tv_day)
        rvScheduleTime = view.findViewById(R.id.rv_schedule_time)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDay.text = weekDay
        val linearLayout = LinearLayoutManager(context)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        rvScheduleTime.layoutManager = linearLayout
        rvScheduleTime.adapter = TimeAdapter(scheduleTimes)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(weekDay: String) =
                ScheduleFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_WEEK_DAY, weekDay)
                    }
                }
    }
}