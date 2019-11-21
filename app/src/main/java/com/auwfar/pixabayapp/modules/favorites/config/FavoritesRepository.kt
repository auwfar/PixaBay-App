package com.auwfar.pixabayapp.modules.favorites.config

import androidx.lifecycle.LiveData

class FavoritesRepository(private val favoritesDao: FavoritesDao) {
    val listFavorites: LiveData<List<FavoritesEntity>> = favoritesDao.getAllFavorites()

    suspend fun insert(data: FavoritesEntity) {
        favoritesDao.insert(data)
    }

    suspend fun delete(id: Long) {
        favoritesDao.delete(id)
    }

}