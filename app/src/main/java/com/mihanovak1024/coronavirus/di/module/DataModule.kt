package com.mihanovak1024.coronavirus.di.module

import com.mihanovak1024.coronavirus.data.DataRepository
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.data.database.RoomDatabase
import com.mihanovak1024.coronavirus.data.network.CoronaVirusGithubDataSource
import com.mihanovak1024.coronavirus.data.network.CoronaVirusGithubDataWebservice
import com.mihanovak1024.coronavirus.data.network.DataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    @Named("coronaVirusGithubDataSource")
    fun provideDataSource(webservice: CoronaVirusGithubDataWebservice): DataSource = CoronaVirusGithubDataSource(webservice)

    @Singleton
    @Provides
    fun provideWebService(): CoronaVirusGithubDataWebservice {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

        return retrofit.create(CoronaVirusGithubDataWebservice::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(): RoomDatabase = RoomDatabase()

    @Singleton
    @Provides
    fun provideRepository(@Named("coronaVirusGithubDataSource") dataSource: DataSource, roomDatabase: RoomDatabase): Repository = DataRepository(dataSource, roomDatabase)

}