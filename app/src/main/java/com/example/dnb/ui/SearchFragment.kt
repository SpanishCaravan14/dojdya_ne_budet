package com.example.dnb.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dnb.databinding.FragmentSearchBinding
import kotlin.getValue
import com.example.dnb.R
import com.example.dnb.data.Result
import com.example.dnb.models.geoApi.localDataModels.GeoInfoLocal
import com.example.dnb.ui.adapters.GeoRVAdapter

class SearchFragment : Fragment(){
    private lateinit var binding: FragmentSearchBinding

    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private lateinit var mainContainerView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainContainerView = requireActivity().findViewById(R.id.mainContainerView)
        mainContainerView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

        binding.cancelSearch.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.searchCity.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(newLocationQuery: String?): Boolean {
                if (newLocationQuery.isNullOrEmpty()) {
                    showPlaceholder(getString(R.string.search_your_city))
                } else if (newLocationQuery.length <= 2) {
                    showPlaceholder(getString(R.string.no_locations_found))
                } else {
                    fetchLocation(newLocationQuery)
                }
                return true
            }

            override fun onQueryTextChange(locationQuery: String?): Boolean {
                if (locationQuery.isNullOrEmpty()) {
                    showPlaceholder(getString(R.string.search_your_city))
                }
                return true
            }
        })


        observeViewModelGeoResult()
    }

    private fun fetchLocation(newLocationQuery: String) {
        showPlaceholder(getString(R.string.loading))
        weatherViewModel.getGeo(newLocationQuery)
        }

    private fun observeViewModelGeoResult() {
        weatherViewModel.geoResult.observe(viewLifecycleOwner) { newGeoResult ->
        when(newGeoResult) {
            is Result.Success<List<GeoInfoLocal>> -> {
                 setLocationRecyclerViewUIData(newGeoResult.data)
            }

            else -> {
                showPlaceholder(getString(R.string.no_locations_found))
            }
        }
        }
    }

    private fun setLocationRecyclerViewUIData(locationData: List<GeoInfoLocal>) {

        if (locationData.isEmpty()) {
            showPlaceholder(getString(R.string.no_locations_found))
        } else {
            binding.citiesRecyclerView.visibility = View.VISIBLE
            binding.searchPlaceholderTV.visibility = View.GONE

            val locationRVLinearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.citiesRecyclerView.setHasFixedSize(true)
            binding.citiesRecyclerView.layoutManager = locationRVLinearLayoutManager

            val locationAdapter = GeoRVAdapter(locationData, weatherViewModel)
            binding.citiesRecyclerView.adapter = locationAdapter
        }
    }

    private fun showPlaceholder(message: String) {
        binding.searchPlaceholderTV.text = message
        binding.citiesRecyclerView.visibility = View.GONE
        binding.searchPlaceholderTV.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        showPlaceholder(getString(R.string.search_your_city))
    }

    override fun onDestroy() {
        super.onDestroy()
        mainContainerView.background = ContextCompat.getDrawable(requireContext(), R.drawable.home_background)
    }

}