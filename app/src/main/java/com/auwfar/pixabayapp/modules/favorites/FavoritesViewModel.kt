package com.auwfar.pixabayapp.modules.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.auwfar.pixabayapp.modules.favorites.config.FavoritesEntity
import com.auwfar.pixabayapp.modules.favorites.config.FavoritesRepository
import com.auwfar.pixabayapp.utils.ImagesDatabase
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoritesRepository

    val listImages: LiveData<List<FavoritesEntity>>

    init {
        val database = ImagesDatabase.getDatabase(application)
        val newsDao = database.favoritesDao()
        repository = FavoritesRepository(newsDao)

        listImages = repository.listFavorites
    }

    fun insertImages(data: FavoritesEntity) = viewModelScope.launch {
        repository.insert(data)
    }

    fun deleteImages(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
}