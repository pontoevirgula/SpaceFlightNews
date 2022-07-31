package com.chslcompany.spaceflightnews.core

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NetworkUtil(
    private val context: Context,
) : DefaultLifecycleObserver {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private var connectivityManager: ConnectivityManager? = null
    private val validNetworks = HashSet<Network>()

    private lateinit var job: Job
    private lateinit var coroutineScope: CoroutineScope

    private val _networkAvailableStateFlow: MutableStateFlow<NetworkState> =
        MutableStateFlow(NetworkState.Available)
    val networkAvailableStateFlow
        get() = _networkAvailableStateFlow

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        initNetwork()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        registerNetworkCallback()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        unRegisterNetworkCallback()
    }

    private fun initNetwork(){
        connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

   private fun registerNetworkCallback(){
        initCoroutine()
        initNetworkMonitoring()
        checkCurrentNetworkState()
    }

    private fun unRegisterNetworkCallback(){
        validNetworks.clear()
        connectivityManager?.unregisterNetworkCallback(networkCallback)
        job.cancel()
    }

    private fun initCoroutine() {
        job = Job()
        coroutineScope = CoroutineScope(Dispatchers.Default + job)
    }

    private fun initNetworkMonitoring() {
        networkCallback = createNetworkCallback()

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            connectivityManager?.getNetworkCapabilities(network).also {
                if (it?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
                    validNetworks.add(network)
                }
            }
            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkCurrentNetworkState() {
        connectivityManager?.activeNetwork?.let {
            validNetworks.addAll(listOf(it))
        }
        checkValidNetworks()
    }

    private fun checkValidNetworks() {
        coroutineScope.launch {
            _networkAvailableStateFlow.emit(
                if (validNetworks.size > 0)
                    NetworkState.Available
                else
                    NetworkState.Unavailable
            )
        }
    }
}

sealed class NetworkState {
    object Unavailable : NetworkState()
    object Available : NetworkState()
}