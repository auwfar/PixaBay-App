package com.auwfar.pixabayapp.network

import com.auwfar.pixabayapp.modules.images.ResponseImages
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkRoutes {
    @GET("api/")
    fun getImages(
        @Query("category") category: String
    ) : retrofit2.Call<ResponseImages>


    @GET("api/")
    fun searchImages(
        @Query("q") query: String
    ) : retrofit2.Call<ResponseImages>
}