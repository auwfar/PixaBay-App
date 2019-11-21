package com.auwfar.pixabayapp.network

interface NetworkListener {
    fun onSuccess()
    fun onFailure(msg: String?)
}