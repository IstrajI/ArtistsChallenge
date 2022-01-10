package com.example.android.artistchalenge.di

import androidx.lifecycle.ViewModel
import com.example.android.artistchalenge.ui.main.bookmark.BookmarkViewModel
import com.example.android.artistchalenge.ui.details.DetailsViewModel
import com.example.android.artistchalenge.ui.main.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(searchViewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkViewModel::class)
    abstract fun bindBookmarkViewModel(bookmarkViewModel: BookmarkViewModel): ViewModel
}