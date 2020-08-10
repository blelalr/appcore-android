package com.android.app_aqi.add

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
    private lateinit var rvAllSite: RecyclerView
    private lateinit var sharedViewModel : SharedViewModel
    private var onDismissListener: DialogInterface.OnDismissListener? = null

    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?) {
        this.onDismissListener = onDismissListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_site_list_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvAllSite = view.findViewById(R.id.list)
        rvAllSite.layoutManager = LinearLayoutManager(context)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.getAllSiteList().observe(this.viewLifecycleOwner, Observer {
            rvAllSite.adapter?.notifyDataSetChanged()
            rvAllSite.adapter = SiteListItemAdapter(it, sharedViewModel)
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog!!)
        if (onDismissListener != null) {
            onDismissListener!!.onDismiss(dialog)
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