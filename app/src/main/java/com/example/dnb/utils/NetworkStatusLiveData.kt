package com.example.dnb.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import java.net.UnknownHostException

class NetworkStatusLiveData (context: Context) : LiveData<Boolean>() {

    companion object {
        const val ROOT_SERVER_CHECK_URL = "www.google.com"
    }

    private val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networks: ArrayList<Network> = ArrayList()
    private var status : Boolean = false

    private val networkStateObject = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            networks.remove(network)
            val checkResult = networks.all { checkInternetConnectivity(it) }
            if(checkResult != status){
                status = checkResult
                postValue(checkResult)
            }
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            networks.add(network)
            val checkResult = checkInternetConnectivity(network)
            if(checkResult != status){
                status = checkResult
                postValue(checkResult)
            }
        }

        fun checkInternetConnectivity(network: Network): Boolean {
            return try {
                network.getByName(ROOT_SERVER_CHECK_URL) != null
            } catch (e: UnknownHostException) {
                false
            }
        }
    }
    override fun onActive() {
        super.onActive()
        connectivityManager.registerNetworkCallback(networkRequestBuilder(), networkStateObject)
        postValue(false) // Consider all networks "unavailable" on start
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkStateObject)
    }

    private fun networkRequestBuilder(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }
}