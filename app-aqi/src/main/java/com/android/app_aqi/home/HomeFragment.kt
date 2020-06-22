package com.android.app_aqi.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.viewpager2.widget.ViewPager2
import com.android.app_aqi.main.MainActivity

import com.android.app_aqi.R
import com.google.android.material.transition.MaterialContainerTransform

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "position"
//private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var position: Int? = null
//    private var param2: String? = null
    private lateinit var siteViewPager: ViewPager2
    private lateinit var siteSiteViewPagerAdapter: SiteViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }

        //設定進入的動畫
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        siteViewPager = view.findViewById(R.id.vp_main)
        initViewPager()
        return view
    }

    private fun initViewPager() {
        siteSiteViewPagerAdapter = SiteViewPagerAdapter(activity!!, (activity as MainActivity).siteList)
        siteViewPager.adapter = siteSiteViewPagerAdapter
        arguments?.getInt(ARG_PARAM1)?.let { siteViewPager.setCurrentItem(it, false) }
    }


    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, position)
                    }
                }
    }
}
