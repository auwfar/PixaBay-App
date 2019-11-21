package com.auwfar.pixabayapp.modules.images.config

import androidx.lifecycle.MutableLiveData
import com.auwfar.pixabayapp.modules.images.ImageModel
import com.auwfar.pixabayapp.modules.images.ResponseImages
import com.auwfar.pixabayapp.network.NetworkListener
import com.auwfar.pixabayapp.network.NetworkRoutes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagesRepository(private val networkRoutes: NetworkRoutes) {
    val _result = MutableLiveData<List<ImageModel>>()
    val _searchResult = MutableLiveData<List<ImageModel>>()

    fun getImages(networkListener: NetworkListener?, category: String) {
        networkRoutes.getImages(category).enqueue(object : Callback<ResponseImages> {
            override fun onResponse(call: Call<ResponseImages>, response: Response<ResponseImages>) {
                if (response.isSuccessful) {
                    networkListener?.onSuccess()
                    val data = response.body()?.response
                    _result.value = data
                } else{
                    networkListener?.onFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ResponseImages>, t: Throwable) {
                networkListener?.onFailure(t.message)
            }
        })
    }

    fun searchImages(networkListener: NetworkListener?, query: String) {
        networkRoutes.searchImages(query).enqueue(object : Callback<ResponseImages> {
            override fun onResponse(call: Call<ResponseImages>, response: Response<ResponseImages>) {
                if (response.isSuccessful) {
                    networkListener?.onSuccess()
                    _searchResult.value = response.body()?.response
                } else{
                    networkListener?.onFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ResponseImages>, t: Throwable) {
                networkListener?.onFailure(t.message)
            }
        })
    }
}