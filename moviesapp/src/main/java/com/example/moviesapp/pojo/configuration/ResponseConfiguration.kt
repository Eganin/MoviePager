package com.example.moviesapp.pojo.configuration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseConfiguration (
    val images: Images,

    @SerialName("change_keys")
    val changeKeys: List<String>
)