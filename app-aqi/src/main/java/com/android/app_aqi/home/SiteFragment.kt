package com.android.app_aqi.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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
        fun newInstance(aqi : AqiModel): SiteFragment {
            val args = Bundle()
            args.putSerializable(AqiModel::class.simpleName, aqi)
            val fragment = SiteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: SharedViewModel
    private lateinit var aqi: AqiModel
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

        aqi = arguments!!.getSerializable(AqiModel::class.simpleName) as AqiModel
        siteName.text = aqi.siteName

        aqi.siteId?.let { siteId ->
            viewModel.getLast12HourAqiDataBySiteId(siteId).observe(this.viewLifecycleOwner, Observer {
                rvPollutions.adapter = PollutionsAdapter(Constant.PollutionType.values(), it)

                if (it.isNotEmpty() && it[0].aQI?.length != 0) {
                    setBackgroundColorByAqi(it[0].aQI!!.toInt())
                }
                siteAqi.text = it[0].aQI

            })
        }

        ViewCompat.setTransitionName(root, aqi.siteId)

    }

    override fun onStart() {
        super.onStart()
        parentFragment?.startPostponedEnterTransition()
    }

    private fun setBackgroundColorByAqi(value: Int){
        when  {
            value <= 50 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_green_l1, null)
            value <= 100 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_yellow_l2, null)
            value <= 150 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_orange_l3, null)
            value <= 200 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_red_l4, null)
            value <= 300 -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_purple_l5, null)
            else -> llAqi.background = resources.getDrawable(R.drawable.bg_ring_dark_purple_l6, null)
        }
    }

}
