package com.example.dnb.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dnb.R
import com.example.dnb.constants.WeatherImageMapper
import com.example.dnb.data.Result
import com.example.dnb.databinding.FragmentNextWeekBinding
import com.example.dnb.models.WeeklyWeatherRVItem
import com.example.dnb.models.weatherApi.localDataModels.WeatherWeekInfoLocal
import com.example.dnb.ui.adapters.WeeklyWeatherRVAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class NextWeekFragment : Fragment() {

    private lateinit var binding: FragmentNextWeekBinding

    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNextWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel.weatherWeekResult.observe(viewLifecycleOwner) {newWeatherWeekInfo ->
        when(newWeatherWeekInfo){
          is Result.Success<WeatherWeekInfoLocal> -> setDailyWeatherRecyclerViewUIData(newWeatherWeekInfo.data)
          else -> Toast.makeText(context, resources.getString(R.string.error_fetching_weather), Toast.LENGTH_LONG).show()
         }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setDailyWeatherRecyclerViewUIData(weatherWeekInfo: WeatherWeekInfoLocal) {
        val tomorrowWeatherTemp = ("${weatherWeekInfo.dayForecastList[1].mintempC.toInt()} /${weatherWeekInfo.dayForecastList[1].maxtempC.toInt()}")
        binding.tomorrowWeatherTemperature.text = tomorrowWeatherTemp
        binding.sunriseTime.text = weatherWeekInfo.tomorrowSunrise
        binding.sunsetTime.text = weatherWeekInfo.tomorrowSunset
        binding.tomorrowWeatherIcon.setImageResource(WeatherImageMapper.getImageForWeatherCode(weatherWeekInfo.dayForecastList[1].condition?.code ?:1000))

       val weeklyWeatherRVModelList = ArrayList<WeeklyWeatherRVItem>()
        for(i in 2..7) {
            val day = resources.getString(weatherWeekInfo.dayForecastList[i].dayOfWeekCode)
            val weatherTemp = ("${weatherWeekInfo.dayForecastList[i].mintempC.toInt()} /${weatherWeekInfo.dayForecastList[i].maxtempC.toInt()}")
            val weatherIcon = WeatherImageMapper.getImageForWeatherCode(weatherWeekInfo.dayForecastList[i].condition.code ?:1000)
            weeklyWeatherRVModelList.add(WeeklyWeatherRVItem(day, weatherTemp, weatherIcon))
        }

        val next7DaysWeatherRVLinearLayoutManager = object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.weekWeatherRV.setHasFixedSize(true)
        binding.weekWeatherRV.layoutManager = next7DaysWeatherRVLinearLayoutManager
        val next7DaysRVAdapter = WeeklyWeatherRVAdapter(weeklyWeatherRVModelList)
        binding.weekWeatherRV.adapter = next7DaysRVAdapter
    }



}