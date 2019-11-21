package com.auwfar.pixabayapp.modules.favorites.config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_images")
data class FavoritesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "tags")
    val tags: String,
    @ColumnInfo(name = "preview_url")
    val previewURL: String,
    @ColumnInfo(name = "image_url")
    val imageURL: String,
    @ColumnInfo(name = "image_width")
    val imageWidth: Long,
    @ColumnInfo(name = "image_height")
    val imageHeight: Long,
    @ColumnInfo(name = "user")
    val user: String,
    @ColumnInfo(name = "user_image_url")
    val userImageURL: String
)