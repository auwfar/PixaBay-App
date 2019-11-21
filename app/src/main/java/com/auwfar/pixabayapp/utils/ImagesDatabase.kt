package com.auwfar.pixabayapp.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.auwfar.pixabayapp.modules.favorites.config.FavoritesDao
import com.auwfar.pixabayapp.modules.favorites.config.FavoritesEntity

@Database(entities = [FavoritesEntity::class], version = 1, exportSchema = false)
abstract class ImagesDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: ImagesDatabase? = null

        fun getDatabase(context: Context): ImagesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImagesDatabase::class.java,
                    "database_images"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}