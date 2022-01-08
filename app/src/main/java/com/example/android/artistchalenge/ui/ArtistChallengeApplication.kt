package com.example.android.artistchalenge.ui

import android.app.Application
import com.example.android.artistchalenge.di.AppComponent
import com.example.android.artistchalenge.di.AppModule
import com.example.android.artistchalenge.di.DaggerAppComponent

class ArtistChallengeApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    }

    fun getComponent(): AppComponent {
        return appComponent
    }
}