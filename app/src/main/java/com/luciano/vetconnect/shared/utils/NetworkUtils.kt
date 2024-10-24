package com.luciano.vetconnect.shared.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.luciano.vetconnect.shared.data.models.Veterinary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import android.util.Log

suspend fun fetchVeterinarias(): List<Veterinary> {
    return withContext(Dispatchers.IO) {
        try {
            val url = "https://mocki.io/v1/34bccc77-5d0a-4787-bbbf-b6e107d439ab"
            val response = URL(url).readText()
            Log.d("VetConnect", "API Response: $response")

            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("veterinaryCards")

            val veterinarias = mutableListOf<Veterinary>()
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val rating = item.getInt("rating")
                Log.d("VetConnect", "Veterinaria ${item.getString("name")} rating: $rating")

                val veterinary = Veterinary(
                    name = item.getString("name"),
                    address = item.getString("address"),
                    rating = rating,
                    clinicPrice = item.getString("clinicPrice"),
                    bathPrice = item.getString("bathPrice")
                )
                veterinarias.add(veterinary)
            }
            veterinarias
        } catch (e: Exception) {
            Log.e("VetConnect", "Error fetching data: ${e.message}", e)
            throw e
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}