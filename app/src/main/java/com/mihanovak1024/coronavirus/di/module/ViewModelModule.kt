package com.mihanovak1024.coronavirus.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.mihanovak1024.coronavirus.data.CoronaActivityViewModel
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.home.HomeFragmentViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
        includes = [
            DataModule::class
        ]
)
class ViewModelModule {

    @Singleton
    @Provides
    fun provideHomeFragmentViewModel(repository: Repository): HomeFragmentViewModel = HomeFragmentViewModel(repository)

    @Singleton
    @Provides
    fun provideActivityFragmentViewModel(viewModelStoreOwner: ViewModelStoreOwner): CoronaActivityViewModel = ViewModelProvider(viewModelStoreOwner).get(CoronaActivityViewModel::class.java)

}