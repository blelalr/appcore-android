package com.android.app_aqi.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.Constant
import com.android.app_aqi.R
import com.android.app_aqi.SharedViewModel
import com.android.app_aqi.home.PollutionsAdapter
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel

class SiteListAdapter(private val siteList : List<AqiModel>,  private val listener: ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_TYPE_CONTENT = 0
    private val ITEM_TYPE_FOOTER = 1

    private var mFooterCount : Int = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == ITEM_TYPE_CONTENT) {
            ListViewHolder(inflater, parent)
        } else {
            FooterViewHolder(inflater, parent)
        }

    }

    override fun getItemCount(): Int {
        return siteList.size + mFooterCount
    }

    override fun getItemViewType(position: Int): Int {
        return if(mFooterCount!= 0 && position > siteList.size - 1 ) {
            ITEM_TYPE_FOOTER
        } else {
            ITEM_TYPE_CONTENT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                val site: AqiModel = siteList[position]
                holder.setIsRecyclable(false)
                holder.bind(site)
                ViewCompat.setTransitionName(holder.itemRoot, site.siteId)
                // Setup shared element transition
                holder.itemRoot.setOnClickListener {
                    listener.onItemClick(holder.itemRoot, position)
                }
            }
            is FooterViewHolder -> {
                ViewCompat.setTransitionName(holder.footRoot, "Footer")
                holder.footRoot.setOnClickListener {
                    listener.onAddClick(holder.footRoot)
                }
            }
        }

    }

    class ListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
        private var siteNameText: TextView = itemView.findViewById(R.id.tv_site_name)
        private var siteAqiText: TextView = itemView.findViewById(R.id.tv_aqi)
        private var statusText: TextView = itemView.findViewById(R.id.tv_status)
        private var aqiLevel: View = itemView.findViewById(R.id.v_aqi_level)
        var itemRoot: CardView = itemView.findViewById(R.id.list_item_root)
        private var context = parent.context

        fun bind(site: AqiModel) {
            siteNameText.text = site.siteName
            siteAqiText.text = site.aQI
            statusText.text = site.status
            site.aQI?.toInt()?.let { setBackgroundColorByAqi(it, context) }

        }

        private fun setBackgroundColorByAqi(value: Int, context: Context){
            when {
                value <= 50 -> aqiLevel.setBackgroundColor(context.resources.getColor(R.color.color_green_L1))
                value <= 100 -> aqiLevel.setBackgroundColor(context.resources.getColor(R.color.color_yellow_L2))
                value <= 150 -> aqiLevel.setBackgroundColor(context.resources.getColor(R.color.color_orange_L3))
                value <= 200 -> aqiLevel.setBackgroundColor(context.resources.getColor(R.color.color_red_L4))
                value <= 300 -> aqiLevel.setBackgroundColor(context.resources.getColor(R.color.color_purple_L5))
                else -> aqiLevel.setBackgroundColor(context.resources.getColor(R.color.color_dark_purple_L6))
            }
        }

    }

    interface ItemClickListener {
        fun onItemClick(itemView: View, position: Int)
        fun onAddClick(footRoot: CardView)
    }
    class FooterViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_footer, parent, false)) {
        var footRoot : CardView = itemView.findViewById(R.id.list_footer_root)

    }

}

