package com.auwfar.pixabayapp.modules.images.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auwfar.pixabayapp.modules.images.ImagesViewModel
import java.lang.IllegalArgumentException

class ImagesVMFactory(private val repository: ImagesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImagesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImagesViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}