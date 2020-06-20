package com.android.app_aqi.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.app_aqi.R
import com.android.app_aqi.model.SiteModel

class SiteFragment : Fragment() {
    companion object {
        fun newInstance(aqi : SiteModel): SiteFragment{
            val args = Bundle()
            args.putSerializable(SiteModel::class.simpleName, aqi)
            val fragment = SiteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var aqi: SiteModel
    private lateinit var viewModel: SiteViewModel
    private lateinit var siteName: TextView
    private lateinit var siteAqi: TextView
    private lateinit var root: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.site_fragment, container, false)
        siteName = view.findViewById(R.id.tv_site_name)
        siteAqi = view.findViewById(R.id.tv_aqi)
        root = view.findViewById(R.id.cl_root)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SiteViewModel::class.java)

        aqi = arguments!!.getSerializable(SiteModel::class.simpleName) as SiteModel
        siteName.text = aqi.siteName

        siteAqi.text = aqi.aQI

        var aqiNumber = aqi.aQI?.toInt()
        setUIByAqi(aqiNumber!!)



    }

    fun setUIByAqi(value: Int){
        var level = 0
        when {
            value == 0 -> level = 0
            value <= 50 -> level = 1
            value <= 100 -> level = 2
            value <= 150 -> level = 3
            value <= 200 -> level = 4
            value <= 300 -> level = 5
            value > 300 -> level = 6
        }


        when (value) {
            in 0..21 -> root.setBackgroundResource(R.color.color_green_L1)
            in 21..22 -> root.setBackgroundResource(R.color.color_yellow_L2)
            in 23..24 -> root.setBackgroundResource(R.color.color_orange_L3)
            in 24..25 -> {
                root.setBackgroundResource(R.color.color_red_L4)
                siteName.setTextColor(getResources().getColor(R.color.color_white_word))
                siteAqi.setTextColor(getResources().getColor(R.color.color_white_word))
            }
            in 28..30 -> {
                root.setBackgroundResource(R.color.color_purple_L5)
                siteName.setTextColor(getResources().getColor(R.color.color_white_word))
                siteAqi.setTextColor(getResources().getColor(R.color.color_white_word))
            }
            else -> {
                root.setBackgroundResource(R.color.color_dark_purple_L6)
                siteName.setTextColor(getResources().getColor(R.color.color_white_word))
                siteAqi.setTextColor(getResources().getColor(R.color.color_white_word))
            }
        }
    }

}
