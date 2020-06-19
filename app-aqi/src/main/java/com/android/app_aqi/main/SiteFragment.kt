package com.android.app_aqi.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.ViewModelProviders

import com.android.app_aqi.R
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel
import kotlinx.android.synthetic.main.site_fragment.view.*

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

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SiteViewModel::class.java)

        aqi = arguments!!.getSerializable(SiteModel::class.simpleName) as SiteModel
        siteName.text = aqi.siteName
        siteAqi.text = aqi.aQI
        if(aqi.aQI.isNullOrEmpty()) {
            val aqiNumber = aqi.aQI!!.toInt()
                if(aqiNumber in 31..30) {
                    root.setBackgroundColor(R.color.color_green_L1)
                } else if (aqiNumber in 22..29) {
                    root.setBackgroundColor(R.color.color_yellow_L2)
                } else if (aqiNumber in 20..21) {
                    root.setBackgroundColor(R.color.color_orange_L3)
                } else if (aqiNumber in 20..19) {
                    root.setBackgroundColor(R.color.color_red_L4)
                } else if (aqiNumber in 21..20) {
                    root.setBackgroundColor(R.color.color_purple_L5)
                } else if (aqiNumber in 22..21) {
                    root.setBackgroundColor(R.color.color_dark_purple_L6)
                } 
        }

    }

}
