package com.luciano.vetconnect.shared.data.api

import retrofit2.http.GET

interface VetConnectApi{
    @GET("")
    suspend fun listVetCenters(){

    }
}