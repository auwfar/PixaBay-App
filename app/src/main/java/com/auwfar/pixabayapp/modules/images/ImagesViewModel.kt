package com.auwfar.pixabayapp.modules.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.auwfar.pixabayapp.modules.images.config.ImagesRepository
import com.auwfar.pixabayapp.network.NetworkListener

class ImagesViewModel(private val repository: ImagesRepository) : ViewModel() {
    var networkListener: NetworkListener? = null

    fun getImages(): LiveData<List<ImageModel>> = repository._result
    fun fetchData(category: String) = repository.getImages(networkListener, category)

    fun getSearchResult(): LiveData<List<ImageModel>> = repository._searchResult
    fun searchData(query: String) = repository.searchImages(networkListener, query)
}