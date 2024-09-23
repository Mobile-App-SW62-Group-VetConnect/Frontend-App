package com.luciano.vetconnect.shared.data.api

import com.luciano.vetconnect.shared.data.models.VeterinaryResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("v1/da0e7130-7d4a-41af-9cf4-80650db94308")
    suspend fun getVeterinaryCards(): VeterinaryResponse
}

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mocki.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}