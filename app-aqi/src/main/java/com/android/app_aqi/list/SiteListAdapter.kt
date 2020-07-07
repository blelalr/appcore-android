package com.android.app_aqi.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.R
import com.android.app_aqi.model.SiteModel

class SiteListAdapter(private val siteList : List<SiteModel>, private val listener: ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM_TYPE_CONTENT: Int = 0
    private val ITEM_TYPE_FOOTER: Int = 1

    private var mContext: Context? = null
    private var mFooterCount : Int = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        return if(viewType == ITEM_TYPE_CONTENT) {
            ListViewHolder(inflater, parent)
        } else {
            FooterViewHolder(inflater, parent)
        }

    }

    override fun getItemCount(): Int {
        return 3 + mFooterCount
    }

    override fun getItemViewType(position: Int): Int {
        return if(mFooterCount!= 0 && position > 3 - 1 ) {
            ITEM_TYPE_FOOTER
        } else {
            ITEM_TYPE_CONTENT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                val site: SiteModel = siteList[position]
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
        var siteNameText: TextView = itemView.findViewById(R.id.tv_site_name)
        var siteAqiText: TextView = itemView.findViewById(R.id.tv_aqi)
        var itemRoot: CardView = itemView.findViewById(R.id.list_item_root)
        private var context = parent.context

        fun bind(site: SiteModel) {
            siteNameText.text = site.siteName
            siteAqiText.text = site.aQI
            setBackgroundColorByAqi(site.aQI?.toInt(), context)

        }

        private fun setBackgroundColorByAqi(value: Int?, context: Context){
            when (value) {
                in 0..21 -> itemRoot.setCardBackgroundColor(context.resources.getColor(R.color.color_green_L1))
                in 21..22 -> itemRoot.setCardBackgroundColor(context.resources.getColor(R.color.color_yellow_L2))
                in 23..24 -> itemRoot.setCardBackgroundColor(context.resources.getColor(R.color.color_orange_L3))
                in 24..25 -> {
                    itemRoot.setCardBackgroundColor(context.resources.getColor(R.color.color_red_L4))
                    siteNameText.setTextColor(context.resources.getColor(R.color.color_white_word))
                    siteAqiText.setTextColor(context.resources.getColor(R.color.color_white_word))
                }
                in 28..30 -> {
                    itemRoot.setCardBackgroundColor(context.resources.getColor(R.color.color_purple_L5))
                    siteNameText.setTextColor(context.resources.getColor(R.color.color_white_word))
                    siteAqiText.setTextColor(context.resources.getColor(R.color.color_white_word))
                }
                else -> {
                    itemRoot.setCardBackgroundColor(context.resources.getColor(R.color.color_dark_purple_L6))
                    siteNameText.setTextColor(context.resources.getColor(R.color.color_white_word))
                    siteAqiText.setTextColor(context.resources.getColor(R.color.color_white_word))
                }
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

