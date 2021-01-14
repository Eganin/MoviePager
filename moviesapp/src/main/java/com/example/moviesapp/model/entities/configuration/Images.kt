package com.example.moviesapp.model.entities.configuration


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Images (
    @SerialName("base_url")
    val baseURL: String,

    @SerialName("secure_base_url")
    val secureBaseURL: String,

    @SerialName("backdrop_sizes")
    val backdropSizes: @RawValue List<String>,

    @SerialName("logo_sizes")
    val logoSizes:  @RawValue List<String>,

    @SerialName("poster_sizes")
    val posterSizes:  @RawValue List<String>,

    @SerialName("profile_sizes")
    val profileSizes: @RawValue List<String>,

    @SerialName("still_sizes")
    val stillSizes: @RawValue List<String>
) : Parcelable