package com.example.musiceducation

import android.app.Application
import com.example.musiceducation.data.AppContainer
import com.example.musiceducation.data.DefaultAppContainer
import com.example.musiceducation.utils.SharedPreferencesManager

class MusicEducationApplication : Application() {

    lateinit var container: AppContainer

    companion object {
        lateinit var instance: MusicEducationApplication
    }
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        instance = this
        SharedPreferencesManager.init(this)


    }
}