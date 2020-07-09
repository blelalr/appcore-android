package com.android.app_aqi.add

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.android.app_aqi.Constant
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.room.AqiDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    SiteListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class SiteListDialogFragment : BottomSheetDialogFragment() {
    lateinit var sharedViewModel : SharedViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_site_list_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(requireParentFragment().requireActivity()).get(SharedViewModel::class.java)
        view.findViewById<RecyclerView>(R.id.list)?.layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.list)?.adapter = SiteListItemAdapter(sharedViewModel)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        //refresh all follow site
        val sharedPreferences = requireActivity().getSharedPreferences(Constant.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val setFromSharedPreferences = sharedPreferences.getStringSet(Constant.FOLLOWED_SITE_LIST, mutableSetOf())
        val copyOfSet = setFromSharedPreferences!!.toMutableSet()
        for( followSiteId in sharedViewModel.followedSet) {
            copyOfSet.add(followSiteId)
        }
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.putStringSet(Constant.FOLLOWED_SITE_LIST, copyOfSet)
        editor.apply() // or commit() if really needed


        val aqiDatabase = Room.databaseBuilder(requireParentFragment().requireActivity().applicationContext, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        GlobalScope.launch {
            var followList: MutableList<SiteModel>  = mutableListOf()
            for( followSiteId in sharedViewModel.followedSet) {
                followList.add(aqiDatabase.getAqiDao().getFollowSiteById(followSiteId))
            }
            sharedViewModel.followedSiteList.postValue(followList)
        }
        aqiDatabase.close()
    }

    companion object {
        fun newInstance(itemCount: Int): SiteListDialogFragment =
                SiteListDialogFragment().apply {
//                    arguments = Bundle().apply {
//                        putInt(ARG_ITEM_COUNT, itemCount)
//                    }
                }

    }
}