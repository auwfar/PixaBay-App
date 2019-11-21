package com.auwfar.pixabayapp.modules.favorites.config

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: FavoritesEntity)

    @Query("DELETE FROM favorite_images WHERE id = (:id)")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM favorite_images")
    fun getAllFavorites(): LiveData<List<FavoritesEntity>>
}