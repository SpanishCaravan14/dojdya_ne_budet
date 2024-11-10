package com.example.dnb.ui

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dnb.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import com.example.dnb.models.weatherInfo.WeatherInfoLocal
import com.example.dnb.R
import com.example.dnb.constants.WeatherConstants
import com.example.dnb.constants.WeatherImageMapper
import dagger.hilt.android.AndroidEntryPoint
import com.example.dnb.data.Result
import com.example.dnb.models.weatherInfo.HourlyWeatherRVItem
import com.example.dnb.ui.adapters.HourlyWeatherRVAdapter

@AndroidEntryPoint
class HomeFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: com.example.dnb.databinding.FragmentHomeBinding

   private val weatherViewModel: WeatherViewModel by activityViewModels()
    private lateinit var todayHourlyWeatherRVAdapter: HourlyWeatherRVAdapter
   private lateinit var tomorrowHourlyWeatherRVAdapter: HourlyWeatherRVAdapter
   //TODO = перенести это во viewmodel
    private val todayHourlyWeatherRVModelList: ArrayList<com.example.dnb.models.weatherInfo.HourlyWeatherRVItem> = ArrayList()
    private val tomorrowHourlyWeatherRVModelList:ArrayList<com.example.dnb.models.weatherInfo.HourlyWeatherRVItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        android.view.View.setOnClickListener {
//            androidx.navigation.NavController.navigate(me.tangobee.weathernaut.R.id.action_homeFragment_to_next7DaysFragment)
//        }
//
//        android.view.View.setOnClickListener {
//            androidx.navigation.NavController.navigate(me.tangobee.weathernaut.R.id.action_homeFragment_to_settingsFragment)
//        }
//
//        android.view.View.setOnClickListener {
//            androidx.navigation.NavController.navigate(me.tangobee.weathernaut.R.id.action_homeFragment_to_searchFragment)
//        }
//
        binding.todayIndicator.setOnClickListener {
            changeIndicatorDotPosition(R.id.todayIndicator)
            binding.hourlyWeatherRV.swapAdapter(todayHourlyWeatherRVAdapter, false)

            binding.todayIndicator.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
            binding.tomorrowIndicator.setTextColor(ContextCompat.getColor(requireContext(), R.color.unselectedIndicatorColor))

            val boldTypeface = ResourcesCompat.getFont(requireContext(), R.font.inter_bold)
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.inter)
            binding.todayIndicator.typeface = boldTypeface
            binding.tomorrowIndicator.typeface = typeface

            binding.todayIndicator.isEnabled = false
            binding.tomorrowIndicator.isEnabled = true
        }

        binding.tomorrowIndicator.setOnClickListener {
            changeIndicatorDotPosition(R.id.tomorrowIndicator)
            binding.hourlyWeatherRV.swapAdapter(tomorrowHourlyWeatherRVAdapter, false)

            binding.tomorrowIndicator.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
            binding.todayIndicator.setTextColor(ContextCompat.getColor(requireContext(), R.color.unselectedIndicatorColor))

            val boldTypeface = ResourcesCompat.getFont(requireContext(), R.font.inter_bold)
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.inter)
            binding.tomorrowIndicator.typeface = boldTypeface
            binding.todayIndicator.typeface = typeface

            binding.tomorrowIndicator.isEnabled = false
            binding.todayIndicator.isEnabled = true
        }


// Observer для отслеживания изменения значений погоды
        weatherViewModel.weatherResult.observe(viewLifecycleOwner){
            weatherInfoResult -> when(weatherInfoResult){
                is Result.Success<WeatherInfoLocal> -> {
                    setCurrentWeatherUIInfo(weatherInfoResult.data)
                    setHourlyRecyclerViewUIInfo(weatherInfoResult.data)
                    Log.d("weather result", weatherInfoResult.data.toString())
                }
                is Result.Error ->{
                    Toast.makeText(context, resources.getString(R.string.error_fetching_weather), Toast.LENGTH_LONG).show()
                    Log.d("weather result", "Error")
                }
            }
        }
    }

    private fun setCurrentWeatherUIInfo(weatherInfo: WeatherInfoLocal) {
        val city = if(weatherInfo.city.isEmpty()){""}else {"${weatherInfo.city}\n"}
        val country = weatherInfo.country
        val region = SpannableString("$city$country")
        region.setSpan(
            RelativeSizeSpan(0.7f),
            city.length,
            region.length,
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
        )
        binding.locationName.text = region

        binding.date.text = weatherInfo.current_weather.current.time
        binding.currentWeatherTemperature.text = weatherInfo.current_weather.current.temperature_2m.toInt().toString()
        binding.weatherUnit.text = weatherInfo.current_weather.current_units.temperature_2m
        binding.weatherIcon.setImageResource(WeatherImageMapper.getImageForWeatherCode(weatherInfo.current_weather.current.weather_code))
        binding.currentWeatherType.text = resources.getString(WeatherConstants.getWeatherDescriptionResourceId(weatherInfo.current_weather.current.weather_code))

        val currentPressure = weatherInfo.current_weather.current.pressure_msl.toString() + weatherInfo.current_weather.current_units.pressure_msl
        binding.pressureValue.text = currentPressure

        val windPressure = weatherInfo.current_weather.current.wind_speed_10m.toString() + weatherInfo.current_weather.current_units.wind_speed_10m
        binding.windValue.text = windPressure

        val humidityPressure = weatherInfo.current_weather.current.relative_humidity_2m.toString() + weatherInfo.current_weather.current_units.relative_humidity_2m
        binding.humidityValue.text = humidityPressure
    }


    private fun setHourlyRecyclerViewUIInfo(weatherInfo: WeatherInfoLocal) {
        var currentHourlyWeatherItemPosition = 0
        todayHourlyWeatherRVModelList.clear()
        for(i in 0 .. 23) {
            var time = weatherInfo.hourly_weather.hourly.time[i]
            var isCurrent = false
            if(isCurrentLocalTime(time)) {
                isCurrent = true
                currentHourlyWeatherItemPosition = i
            }

            val weatherIcon = WeatherImageMapper.getImageForWeatherCode(weatherInfo.hourly_weather.hourly.weather_code[i])
            val weatherTemp = weatherInfo.hourly_weather.hourly.temperature_2m[i]

            todayHourlyWeatherRVModelList.add(HourlyWeatherRVItem(time, weatherIcon, "${weatherTemp.toInt()}°", isCurrent))
        }

        tomorrowHourlyWeatherRVModelList.clear()
        for(i in 24 .. 47) {
            val time = weatherInfo.hourly_weather.hourly.time[i]
            val weatherIcon = WeatherImageMapper.getImageForWeatherCode(weatherInfo.hourly_weather.hourly.weather_code[i])
            val weatherTemp = weatherInfo.hourly_weather.hourly.temperature_2m[i]
            tomorrowHourlyWeatherRVModelList.add(HourlyWeatherRVItem(time, weatherIcon, "${weatherTemp.toInt()}°", false))
        }

        binding.hourlyWeatherRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        todayHourlyWeatherRVAdapter = HourlyWeatherRVAdapter(todayHourlyWeatherRVModelList)
        tomorrowHourlyWeatherRVAdapter = HourlyWeatherRVAdapter(tomorrowHourlyWeatherRVModelList)
        binding.hourlyWeatherRV.adapter = todayHourlyWeatherRVAdapter

        binding.hourlyWeatherRV.scrollToPosition(currentHourlyWeatherItemPosition)

        binding.tomorrowIndicator.isEnabled = true
        binding.todayIndicator.isEnabled = true
    }


    private fun changeIndicatorDotPosition(viewId: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.homeFragmentLayout)
        constraintSet.connect(R.id.indicator, ConstraintSet.START, viewId, ConstraintSet.START, 0)
        constraintSet.connect(R.id.indicator, ConstraintSet.END, viewId, ConstraintSet.END, 0)
        constraintSet.applyTo(binding.homeFragmentLayout)
    }

    private fun isCurrentLocalTime(timeString: String): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Calendar.getInstance()
        val parsedTime = sdf.parse(timeString)

        if (parsedTime != null) {
            val parsedCalendar = Calendar.getInstance()
            parsedCalendar.time = parsedTime
            return currentTime.get(Calendar.HOUR_OF_DAY) == parsedCalendar.get(Calendar.HOUR_OF_DAY)
        }
        return false
    }
}