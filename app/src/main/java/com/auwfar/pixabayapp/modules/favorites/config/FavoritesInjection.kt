package com.auwfar.pixabayapp.modules.favorites.config

import android.app.Application
import androidx.lifecycle.ViewModelProvider

object FavoritesInjection {
    fun provideViewModelFactory(application: Application): ViewModelProvider.Factory {
        return FavoritesVMFactory(application)
    }
}