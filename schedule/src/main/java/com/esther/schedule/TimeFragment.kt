package com.esther.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_WEEK_DAY = "weekday"
private const val ARG_DATE = "date"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {
    private lateinit var tvDate: TextView
    private lateinit var rvTime: RecyclerView
    private lateinit var tvWeekday: TextView
    private val times = arrayListOf("09:00", "09:30","10:00", "10:30", "11:00", "11:30", "13:00", "13:30","14:00", "14:30","15:00", "15:30",
            "16:00", "16:30","17:00", "17:30","18:00", "18:30","19:00", "19:30","20:00", "20:30","21:00", "21:30","22:00", "22:30","23:00", "23:30")

    private var weekday: String? = null
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weekday = it.getString(ARG_WEEK_DAY)
            date = it.getString(ARG_DATE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_time, container, false)
        tvWeekday = view.findViewById(R.id.tv_weekday)
        tvDate = view.findViewById(R.id.tv_date)
        rvTime = view.findViewById(R.id.rv_time)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvWeekday.text = weekday
        tvDate.text = date
        val linearLayout = LinearLayoutManager(context)
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        rvTime.layoutManager = linearLayout
        rvTime.adapter = TimeAdapter(times)

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
        fun newInstance(weekday: String, date: String) =
                ScheduleFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_WEEK_DAY, weekday)
                        putString(ARG_DATE, date)
                    }
                }
    }
}