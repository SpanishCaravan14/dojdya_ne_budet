package com.example.dnb.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dnb.R
import com.example.dnb.models.weatherInfo.HourlyWeatherRVItem


/**
 * Адаптер для RecyclerView с почасовым прогнозом погоды на день
 */

class HourlyWeatherRVAdapter(private val hourlyWeatherDataList: ArrayList<HourlyWeatherRVItem>) : RecyclerView.Adapter<HourlyWeatherRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_hourly_weather_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.hourlyWeatherContainer.context
        holder.weatherIcon.setImageResource(hourlyWeatherDataList[position].weatherIcon)
        holder.weatherValue.text = hourlyWeatherDataList[position].weatherTemp

        val isCurrent = hourlyWeatherDataList[position].isCurrent
        val time = hourlyWeatherDataList[position].time
            //Отдельно выделяется элемент, соответствующий текущему времени
        if(isCurrent) {
            val boldTypeface = ResourcesCompat.getFont(context, R.font.inter_semibold)
            holder.weatherTime.setTextColor(ContextCompat.getColor(context, R.color.textColor))
            holder.weatherTime.typeface = boldTypeface
            holder.hourlyWeatherContainer.background = ContextCompat.getDrawable(context, R.drawable.current_hourly_weather_background)
            holder.weatherTime.text = "Сейчас"
        } else {
            val typeface = ResourcesCompat.getFont(context, R.font.inter)
            holder.weatherTime.setTextColor(ContextCompat.getColor(context, R.color.upcomingTimeColor))
            holder.weatherTime.typeface = typeface
            holder.hourlyWeatherContainer.background = ContextCompat.getDrawable(context, R.drawable.hourly_weather_box_background)
            holder.weatherTime.text = time
        }

    }

    override fun getItemCount(): Int {
        return hourlyWeatherDataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hourlyWeatherContainer: LinearLayout = itemView.findViewById(R.id.hourlyWeatherContainer)
        val weatherTime: TextView = itemView.findViewById(R.id.weatherTime)
        val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)
        val weatherValue: TextView = itemView.findViewById(R.id.weatherValue)
    }
}