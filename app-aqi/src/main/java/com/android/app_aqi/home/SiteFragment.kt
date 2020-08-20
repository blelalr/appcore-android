package com.android.app_aqi.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.Constant
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel

class SiteFragment : Fragment() {
    companion object {
        fun newInstance(aqi : SiteModel): SiteFragment {
            val args = Bundle()
            args.putSerializable(SiteModel::class.simpleName, aqi)
            val fragment = SiteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var last12HourAqiDataList: List<AqiModel>? = null
    private lateinit var viewModel: SharedViewModel
    private lateinit var aqi: SiteModel
    private lateinit var siteName: TextView
    private lateinit var siteAqi: TextView
    private lateinit var root: ConstraintLayout
    private lateinit var rvPollutions: RecyclerView
    private lateinit var llAqi: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.site_fragment, container, false)
        siteName = view.findViewById(R.id.tv_site_name)
        siteAqi = view.findViewById(R.id.tv_aqi)
        root = view.findViewById(R.id.cl_root)
        llAqi = view.findViewById(R.id.ll_aqi)
        rvPollutions = view.findViewById(R.id.rv_pollutions)
        rvPollutions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        aqi = arguments!!.getSerializable(SiteModel::class.simpleName) as SiteModel
        siteName.text = aqi.siteName
        siteAqi.text = aqi.aQI
        viewModel.getLast12HourAqiDataBySiteId(aqi.siteId).observe(this.viewLifecycleOwner, Observer {
            last12HourAqiDataList = it
            rvPollutions.adapter = PollutionsAdapter(Constant.PollutionType.values())
        })
        setBackgroundColorByAqi(aqi.aQI?.toInt())
        ViewCompat.setTransitionName(root, aqi.siteId)

    }

    override fun onStart() {
        super.onStart()
        parentFragment?.startPostponedEnterTransition()
    }

    private fun setBackgroundColorByAqi(value: Int?){
//        var level = 0
//        when {
//            value == 0 -> level = 0
//            value <= 50 -> level = 1
//            value <= 100 -> level = 2
//            value <= 150 -> level = 3
//            value <= 200 -> level = 4
//            value <= 300 -> level = 5
//            value > 300 -> level = 6
//        }

        when (value) {
            in 0..21 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_green_l1, null)
            in 21..22 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_yellow_l2, null)
            in 23..24 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_orange_l3, null)
            in 24..25 -> {
                llAqi.background = resources.getDrawable(R.drawable.bg_ring_red_l4, null)
                siteName.setTextColor(resources.getColor(R.color.color_white_word))
                siteAqi.setTextColor(resources.getColor(R.color.color_white_word))
            }
            in 28..30 -> {
                llAqi.background = resources.getDrawable(R.drawable.bg_ring_purple_l5, null)
                siteName.setTextColor(resources.getColor(R.color.color_white_word))
                siteAqi.setTextColor(resources.getColor(R.color.color_white_word))
            }
            else -> {
                llAqi.background = resources.getDrawable(R.drawable.bg_ring_dark_purple_l6, null)
                siteName.setTextColor(resources.getColor(R.color.color_white_word))
                siteAqi.setTextColor(resources.getColor(R.color.color_white_word))
            }
        }

    }

}
