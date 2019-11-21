package com.auwfar.pixabayapp.modules.images.config

import androidx.lifecycle.ViewModelProvider
import com.auwfar.pixabayapp.network.Network

object ImagesInjection {
    private fun provideRepository(): ImagesRepository {
        return ImagesRepository(Network.routes())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ImagesVMFactory(provideRepository())
    }
}