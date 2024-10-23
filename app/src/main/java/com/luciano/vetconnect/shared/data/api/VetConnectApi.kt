package com.luciano.vetconnect.shared.data.api

import com.luciano.vetconnect.shared.data.models.VeterinaryResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("v1/34bccc77-5d0a-4787-bbbf-b6e107d439ab")
    suspend fun getVeterinaryCards(): VeterinaryResponse

    @GET("")
    suspend fun listVetCenters()
}

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mocki.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}