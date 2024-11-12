package com.example.dnb.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dnb.models.geoApi.localDataModels.GeoInfoLocal
import com.example.dnb.R
import com.example.dnb.ui.WeatherViewModel

class GeoRVAdapter(private val geoRVDataList: List<GeoInfoLocal>,
                   private val weatherViewModel: WeatherViewModel) : RecyclerView.Adapter<GeoRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cities_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return geoRVDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cityName.text = geoRVDataList[position].name
        val cityAddress = listOf(
            geoRVDataList[position].name,
            geoRVDataList[position].region,
            geoRVDataList[position].country
        ).filterNot { it.isNullOrBlank() }.joinToString(", ")

        holder.cityAddress.text = cityAddress

        holder.addRemoveCity.setOnClickListener {
            holder.addRemoveCity.setImageResource(R.drawable.icon_right_arrow)
            val context = holder.itemView.context

            weatherViewModel.getGeoWeather("${geoRVDataList[position].lat} ${geoRVDataList[position].long}")

//            val sharedPreferencesHelper = SharedPreferencesHelper(context)
//            sharedPreferencesHelper.saveGeocodingData(geoWeatherModel)

            if (context is Activity) {
                context.onBackPressed()
            }
        }

//        val samePlace = GeocodingHelper.areLocationsSame(lat, long, geoRVDataList[position].latitude, geoRVDataList[position].longitude)
//
//        if(samePlace) {
//            holder.addRemoveCity.setImageResource(R.drawable.icon_right_arrow)
//        } else {
//            holder.addRemoveCity.setImageResource(R.drawable.icon_add)
//        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val cityAddress: TextView = itemView.findViewById(R.id.cityAddress)
        val addRemoveCity: ImageButton = itemView.findViewById(R.id.addRemoveCity)
    }
}