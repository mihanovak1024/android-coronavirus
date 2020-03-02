package com.mihanovak1024.coronavirus.di.module

import com.mihanovak1024.coronavirus.data.DataSource
import com.mihanovak1024.coronavirus.data.network.RetrofitDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDataSource(): DataSource = RetrofitDataSource()

}