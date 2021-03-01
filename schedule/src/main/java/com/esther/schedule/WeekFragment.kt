package com.esther.schedule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeekFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeekFragment : Fragment() {
    private val calendarViewModel: CalenderViewModel by activityViewModels()
    private lateinit var llWeek: LinearLayout
    private lateinit var fragmentTransaction: FragmentTransaction
    private val weekdays = arrayListOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    private val months = arrayListOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            calendar = it.getBundle(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_week, container, false)
        llWeek = view.findViewById(R.id.ll_week_view_root)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAllSchedule()
    }

    private fun addAllSchedule() {
        fragmentTransaction = childFragmentManager.beginTransaction()
        for(index in 1 until weekdays.size +1){
            addScheduleFragment(index, calendarViewModel.getCalender().get(Calendar.DAY_OF_MONTH).toString()+"\n"+months[calendarViewModel.getCalender().get(Calendar.MONTH)])
            calendarViewModel.getCalender().add(Calendar.DAY_OF_WEEK, 1)
        }
        fragmentTransaction.commit()
    }

    private fun addScheduleFragment(index: Int, date: String) {
        val frame = FrameLayout(requireContext())
        val frameParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        frameParams.weight = 1f
        frame.layoutParams = frameParams
        frame.id = index
        val fragment = ScheduleFragment.newInstance(weekday = weekdays[index-1], date = date)
        fragmentTransaction.add(index, fragment)
        llWeek.addView(frame)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                WeekFragment().apply {
                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}