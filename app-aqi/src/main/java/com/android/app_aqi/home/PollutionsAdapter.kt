package com.android.app_aqi.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app_aqi.Constant
import com.android.app_aqi.R
import com.android.app_aqi.model.AqiModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class PollutionsAdapter(private val pollutions: Array<Constant.PollutionType>, private val  last12HourAqiDataList: List<AqiModel>) : RecyclerView.Adapter<PollutionsAdapter.PollutionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollutionViewHolder {
        return PollutionViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int {
        return pollutions.size
    }

    override fun onBindViewHolder(holder: PollutionViewHolder, position: Int) {
        holder.tvPollutionName.text = pollutions[position].pollutionName
        holder.onBind(pollutions[position].columnName, last12HourAqiDataList)
    }


    class PollutionViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.pollution_list_item, parent, false)) {
        internal var tvPollutionName: TextView = itemView.findViewById(R.id.tv_pollution_name)
        var bcPollution: BarChart = itemView.findViewById(R.id.bc_pollution)
        private var dataList : MutableList<String> = mutableListOf()
        fun onBind(columnName: String, last12HourAqiDataList: List<AqiModel>) {
            dataList = mutableListOf()
            when (columnName) {
                "AQI" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.aQI)
                    }
                }

                "SO2" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.sO2)
                    }
                }

                "CO" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.cO)
                    }
                }

                "CO_8hr" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.cO8hr)
                    }
                }

                "O3" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.o3)
                    }
                }

                "O3_8hr" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.o38hr)
                    }
                }

                "PM10" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.pM10)
                    }
                }

                "PM2.5" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.pM25)
                    }
                }

                "NO2" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.nO2)
                    }
                }

                "NOx" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.nOx)
                    }
                }

                "NO" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.nO)
                    }
                }

                "PM2.5_AVG" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.pM25AVG)
                    }
                }

                "PM10_AVG" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.pM10AVG)
                    }
                }

                "SO2_AVG" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.sO2AVG)
                    }
                }

                "WindSpeed" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.windSpeed)
                    }
                }

                "WindDirec" -> {
                    last12HourAqiDataList.forEach {
                        addData(it.windDirec)
                    }
                }
            }
            setBarChart(dataList, last12HourAqiDataList)
            Log.d("esther", "$columnName")
            Log.d("esther", "$dataList")
        }

        private fun setBarChart(dataList: MutableList<String>, last12HourAqiDataList: List<AqiModel>) {
            val entries = ArrayList<BarEntry>()
            for (pos in 0 until dataList.size) {
                entries.add( BarEntry(pos.toFloat() , dataList[pos].toFloat()))
            }

            val labels = ArrayList<String>()
            for (element in last12HourAqiDataList) {
                labels.add(element.publishTime.toString())
            }

            val barDataSet = BarDataSet(entries,"")

            val data = BarData( barDataSet)
            bcPollution.data = data

            bcPollution
        }

        private fun addData(data: String?) {
            if(data.isNullOrEmpty() || data == "-" || data == "" || data == "ND") {
                dataList.add("0")
            } else {
                dataList.add(data)
            }
        }
    }

}
