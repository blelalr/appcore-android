package com.android.app_aqi.home


import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.main.MainActivity
import com.google.android.material.transition.MaterialContainerTransform

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var sharedViewModel: SharedViewModel
    private lateinit var siteViewPager: ViewPager
    private lateinit var siteViewPagerAdapter: SiteViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        setEnterSharedElementCallback(object : androidx.core.app.SharedElementCallback(){
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                val item = sharedViewModel.siteList[siteViewPager.currentItem].siteName
                val itemView = (siteViewPagerAdapter.instantiateItem(siteViewPager, siteViewPager.currentItem) as Fragment).view?.findViewById<ConstraintLayout>(R.id.cl_root) as View
                names?.clear()
                sharedElements?.clear()
                item?.let { 
                    names?.add(it)
                    sharedElements?.put(it, itemView)
                }
            }
        })
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.list_to_pager_enter_transition)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        siteViewPager = view.findViewById(R.id.vp_main)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        siteViewPagerAdapter = SiteViewPagerAdapter(siteList = sharedViewModel.siteList, fm = childFragmentManager)
        siteViewPager.adapter = siteViewPagerAdapter
        siteViewPager.currentItem = sharedViewModel.currentPos
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
