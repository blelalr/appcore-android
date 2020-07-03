package com.android.app_aqi.list

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.R
import com.android.app_aqi.model.SiteModel

class SiteListAdapter(private val siteList : List<SiteModel>, private val listener: ItemClickListener) : RecyclerView.Adapter<SiteListAdapter.ListViewHolder>() {
    var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        return ListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return siteList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val site: SiteModel = siteList[position]
        holder.setIsRecyclable(false)
        holder.bind(site)
        ViewCompat.setTransitionName(holder.itemView, site.siteId)
        // Setup shared element transition
        holder.itemView.setOnClickListener {
            listener.onItemClick(holder.itemView, position)
        }
    }

    class ListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
        var siteNameText: TextView = itemView.findViewById(R.id.tv_site_name)
        var siteAqiText: TextView = itemView.findViewById(R.id.tv_aqi)
        private var context = parent.context

        fun bind(site: SiteModel) {
            siteNameText.text = site.siteName
            siteAqiText.text = site.aQI
            setBackgroundColorByAqi(site.aQI?.toInt(), context)

        }

        private fun setBackgroundColorByAqi(value: Int?, context: Context){
            when (value) {
                in 0..21 -> itemView.setBackgroundResource(R.color.color_green_L1)
                in 21..22 -> itemView.setBackgroundResource(R.color.color_yellow_L2)
                in 23..24 -> itemView.setBackgroundResource(R.color.color_orange_L3)
                in 24..25 -> {
                    itemView.setBackgroundResource(R.color.color_red_L4)
                    siteNameText.setTextColor(context.resources.getColor(R.color.color_white_word))
                    siteAqiText.setTextColor(context.resources.getColor(R.color.color_white_word))
                }
                in 28..30 -> {
                    itemView.setBackgroundResource(R.color.color_purple_L5)
                    siteNameText.setTextColor(context.resources.getColor(R.color.color_white_word))
                    siteAqiText.setTextColor(context.resources.getColor(R.color.color_white_word))
                }
                else -> {
                    itemView.setBackgroundResource(R.color.color_dark_purple_L6)
                    siteNameText.setTextColor(context.resources.getColor(R.color.color_white_word))
                    siteAqiText.setTextColor(context.resources.getColor(R.color.color_white_word))
                }
            }
        }

    }

    interface ItemClickListener {
        fun onItemClick(itemView: View, position: Int)
    }


}

