package com.android.app_aqi.home


import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.google.android.material.bottomappbar.BottomAppBar

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var sharedViewModel: SharedViewModel
    private var menuItemListener: OnMenuItemClickListener? = null
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var siteViewPager: ViewPager
    private lateinit var siteViewPagerAdapter: SiteViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.list_to_pager_enter_transition)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        siteViewPager = view.findViewById(R.id.vp_main)
        bottomAppBar = view.findViewById(R.id.bottom_app_bar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppbar()
        initViewPager()
        prepareTransitions()
    }

    private fun prepareTransitions() {
        setEnterSharedElementCallback(object : SharedElementCallback(){
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                // Locate the image view at the primary fragment (the ImageFragment that is currently
                // visible). To locate the fragment, call instantiateItem with the selection position.
                // At this stage, the method will simply return the fragment at the position and will
                // not create a new one.
                val currentFragment = siteViewPagerAdapter.instantiateItem(siteViewPager, sharedViewModel.currentPos) as Fragment
                val view = currentFragment.view ?: return

                // Map the first shared element name to the child ImageView.
                sharedElements!![names!![0]] = view.findViewById(R.id.cl_root)
            }
        })

    }

    private fun initAppbar() {
        bottomAppBar.replaceMenu(R.menu.bottom_menu)
        bottomAppBar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.show_list -> {
                    if(childFragmentManager.fragments[0].isAdded) {
                        menuItemListener?.onMenuItemClick(childFragmentManager.fragments[0].requireView().findViewById(R.id.cl_root), sharedViewModel.currentPos)
                    }
                    true
                }
                else -> false
            }
        }
    }


    private fun initViewPager() {
        sharedViewModel.allFollowedSite.value?.let {
            siteViewPagerAdapter = SiteViewPagerAdapter(it , fm = childFragmentManager)
            siteViewPager.adapter = siteViewPagerAdapter
            siteViewPager.currentItem = sharedViewModel.currentPos
            siteViewPager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    sharedViewModel.currentPos = position
                }
            })
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragment.OnMenuItemClickListener) {
            menuItemListener = context
        } else {
            throw RuntimeException("$context must implement OnMenuItemClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        menuItemListener = null
    }

    interface OnMenuItemClickListener {
        fun onMenuItemClick(itemView: View, position: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
