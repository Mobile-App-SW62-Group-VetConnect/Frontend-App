package com.luciano.vetconnect.shared.data.models

data class Veterinary(
    val name: String,
    val address: String,
    val rating: Int,
    val clinicPrice: String,
    val bathPrice: String
)

data class VeterinaryResponse(
    val veterinaryCards: List<Veterinary>
)
