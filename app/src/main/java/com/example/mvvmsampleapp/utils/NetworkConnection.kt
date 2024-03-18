package com.example.mvvmsampleapp.utils


import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkConnection(private val context: Context) {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    init {
        registerConnectivityChangeListener()
    }

    private fun registerConnectivityChangeListener() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                _isConnected.postValue(true)
            }

            override fun onLost(network: android.net.Network) {
                _isConnected.postValue(false)
            }
        })
    }
}
