package com.luciano.vetconnect.shared.data.models

data class Veterinary(
    val name: String,
    val address: String,
    val rating: Int,
    val clinicPrice: String,
    val bathPrice: String,
    val imageUrl: String? = null,  // URL para la imagen de la veterinaria
    val services: List<Service> = emptyList()  // Lista de servicios adicionales si se necesitan
) {
    data class Service(
        val name: String,
        val price: String,
        val description: String? = null
    )
}

data class VeterinaryResponse(
    val veterinaryCards: List<Veterinary>
)