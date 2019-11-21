package com.auwfar.pixabayapp.modules.images

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseImages(
    @SerializedName("hits")
    val response: List<ImageModel>
)

@Parcelize
data class ImageModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("previewURL")
    val previewURL: String,
    @SerializedName("largeImageURL")
    val imageURL: String,
    @SerializedName("imageWidth")
    val imageWidth: Long,
    @SerializedName("imageHeight")
    val imageHeight: Long,
    @SerializedName("user")
    val user: String,
    @SerializedName("userImageURL")
    val userImageURL: String
): Parcelable