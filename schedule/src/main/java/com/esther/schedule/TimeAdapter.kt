package com.esther.schedule

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimeAdapter(private val scheduleTimes: ArrayList<String>) : RecyclerView.Adapter<TimeAdapter.WeekViewHolder>() {
    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeAdapter.WeekViewHolder {
        val mLayoutInflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        return WeekViewHolder(mLayoutInflater.inflate(R.layout.time_item, parent, false))

    }

    inner class WeekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTime: TextView = itemView.findViewById(R.id.tv_time)
    }

    override fun getItemCount(): Int {
        return scheduleTimes.size
    }

    override fun onBindViewHolder(holder: TimeAdapter.WeekViewHolder, position: Int) {
        holder.tvTime.text = scheduleTimes[position]
    }

}
