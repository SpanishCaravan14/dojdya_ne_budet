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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dnb.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.dnb.models.weatherApi.localDataModels.WeatherInfoLocal
import com.example.dnb.R
import com.example.dnb.constants.WeatherConstants
import com.example.dnb.constants.WeatherImageMapper
import dagger.hilt.android.AndroidEntryPoint
import com.example.dnb.data.Result
import com.example.dnb.models.HourlyWeatherRVItem
import com.example.dnb.ui.adapters.HourlyWeatherRVAdapter

@AndroidEntryPoint
class HomeFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentHomeBinding

   private val weatherViewModel: WeatherViewModel by activityViewModels()
    private lateinit var todayHourlyWeatherRVAdapter: HourlyWeatherRVAdapter
   private lateinit var tomorrowHourlyWeatherRVAdapter: HourlyWeatherRVAdapter
   //TODO = перенести это во viewmodel
    private val todayHourlyWeatherRVModelList: ArrayList<HourlyWeatherRVItem> = ArrayList()
    private val tomorrowHourlyWeatherRVModelList:ArrayList<HourlyWeatherRVItem> = ArrayList()

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
        binding.weeklyIndicator.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_nextWeekFragment)
        }
        binding.searchCities.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

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

        binding.date.text = weatherInfo.localDayTime
        binding.currentWeatherTemperature.text = weatherInfo.currentWeather.tempC.toInt().toString()
        binding.weatherUnit.text = "°C"
        binding.weatherIcon.setImageResource(WeatherImageMapper.getImageForWeatherCode(weatherInfo.currentWeather.weather_code))
        binding.currentWeatherType.text = resources.getString(WeatherConstants.getWeatherDescriptionResourceId(weatherInfo.currentWeather. weather_code))


        binding.pressureValue.text = "${weatherInfo.currentWeather.pressure_msl} hPa"
        binding.windValue.text = "${weatherInfo.currentWeather.wind_speed_10m} м/с"
        binding.humidityValue.text = "${weatherInfo.currentWeather.relative_humidity_2m}"
    }


    private fun setHourlyRecyclerViewUIInfo(weatherInfo: WeatherInfoLocal) {
        weatherInfo.currentWeather.tempC.toString()

        var currentHourlyWeatherItemPosition = 0
        todayHourlyWeatherRVModelList.clear()
        for(i in 0 .. 23) {
            var time = weatherInfo.hourlyWeather.time[i]
            Log.d("hourly", "$time ${weatherInfo.hourlyWeather.weather_code[i]}")
            val x = weatherInfo.localDayTime
            //Т.к. данные в дневном прогнозе могу отличаться от текущих, передаем в адаптер иконку и температуру отдельно
            if(time.equals(weatherInfo.localTime)) {
                currentHourlyWeatherItemPosition = i
                val weatherIcon = WeatherImageMapper.getImageForWeatherCode(weatherInfo.currentWeather.weather_code)
                val weatherTemp = weatherInfo.currentWeather.tempC
                todayHourlyWeatherRVModelList.add(HourlyWeatherRVItem(time, weatherIcon, "${weatherTemp.toInt()}°", isCurrent = true))
                continue
            }

            val weatherIcon = WeatherImageMapper.getImageForWeatherCode(weatherInfo.hourlyWeather.weather_code[i])
            val weatherTemp = weatherInfo.hourlyWeather.temperature_2m[i]
            todayHourlyWeatherRVModelList.add(HourlyWeatherRVItem(time, weatherIcon, "${weatherTemp.toInt()}°", isCurrent = false))
        }

        tomorrowHourlyWeatherRVModelList.clear()
        for(i in 24 .. 47) {
            val time = weatherInfo.hourlyWeather.time[i]
            val weatherIcon = WeatherImageMapper.getImageForWeatherCode(weatherInfo.hourlyWeather.weather_code[i])
            val weatherTemp = weatherInfo.hourlyWeather.temperature_2m[i]
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

}