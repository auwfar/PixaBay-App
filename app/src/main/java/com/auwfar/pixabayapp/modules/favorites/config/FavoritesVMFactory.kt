package com.auwfar.pixabayapp.modules.favorites.config

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auwfar.pixabayapp.modules.favorites.FavoritesViewModel
import java.lang.IllegalArgumentException

class FavoritesVMFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}