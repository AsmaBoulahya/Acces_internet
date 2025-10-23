package com.example.accesinternet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.TextView
import android.net.Network

class MainActivity : AppCompatActivity() {

    private lateinit var textStatus: TextView
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textStatus = findViewById(R.id.textStatus)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Callback réseau (détecte quand on se connecte / déconnecte)
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    textStatus.text = "✅ Connecté à Internet"
                    textStatus.setTextColor(getColor(android.R.color.holo_green_dark))
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    textStatus.text = "❌ Hors ligne"
                    textStatus.setTextColor(getColor(android.R.color.holo_red_dark))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val request = android.net.NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
