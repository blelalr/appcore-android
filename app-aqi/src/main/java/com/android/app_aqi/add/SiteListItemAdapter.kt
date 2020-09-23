package com.android.app_aqi.add

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.model.SiteModel

class SiteListItemAdapter(private val siteList: List<SiteModel>, private val sharedViewModel: SharedViewModel) : RecyclerView.Adapter<SiteListItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.text.text = siteList[position].siteName
        holder.tbFollow.isChecked = siteList[position].isFollow!!
        holder.tbFollow.setOnCheckedChangeListener { _, isChecked ->
            siteList[position].siteId.let {
                if (isChecked) {
                    siteList[position].isFollow = true
                    sharedViewModel.followSite(it)
                }  else {
                    siteList[position].isFollow = false
                    sharedViewModel.unFollowSite(it)
                }
            }
            holder.tbFollow.isChecked = isChecked
        }
    }

    override fun getItemCount(): Int {
        return siteList.size
    }

    class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_site_list_dialog_list_dialog_item, parent, false)) {
        internal val text: TextView = itemView.findViewById(R.id.text)
        internal val tbFollow: ToggleButton = itemView.findViewById(R.id.tb_follow)
    }
}
