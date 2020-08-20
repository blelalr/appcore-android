package com.android.app_aqi.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.Constant
import com.android.app_aqi.R

class PollutionsAdapter(private val pollutions: Array<Constant.PollutionType>) : RecyclerView.Adapter<PollutionsAdapter.PollutionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollutionViewHolder {
        return PollutionViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return pollutions.size
    }

    override fun onBindViewHolder(holder: PollutionViewHolder, position: Int) {
        holder.tvPollutionName.text = pollutions[position].pollutionName
    }

    class PollutionViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.pollution_list_item, parent, false)) {
        internal var tvPollutionName: TextView = itemView.findViewById(R.id.tv_pollution_name)
    }

}
