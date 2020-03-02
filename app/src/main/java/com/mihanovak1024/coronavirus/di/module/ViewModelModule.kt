package com.mihanovak1024.coronavirus.di.module

import com.mihanovak1024.coronavirus.data.DataSource
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
    fun provideHomeFragmentViewModel(dataSource: DataSource): HomeFragmentViewModel = HomeFragmentViewModel(dataSource)


}