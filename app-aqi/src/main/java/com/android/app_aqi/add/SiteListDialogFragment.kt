package com.android.app_aqi.add

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.model.SiteModel

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
        view.findViewById<RecyclerView>(R.id.list)?.adapter = SiteAdapter(siteList = sharedViewModel.siteList)
    }

    private inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_site_list_dialog_list_dialog_item, parent, false)) {

        internal val text: TextView = itemView.findViewById(R.id.text)
    }

    private inner class SiteAdapter internal constructor(private val siteList: List<SiteModel>)  : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = siteList[position].siteName
        }

        override fun getItemCount(): Int {
            return siteList.size
        }
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