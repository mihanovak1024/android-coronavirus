package com.mihanovak1024.coronavirus.di.module

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


}