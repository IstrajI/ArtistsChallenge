package com.example.android.artistchalenge.di

import com.example.android.artistchalenge.ui.main.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(searchFragment: SearchFragment)
}