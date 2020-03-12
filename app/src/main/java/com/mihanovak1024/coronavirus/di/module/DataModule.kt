package com.mihanovak1024.coronavirus.di.module

import android.content.Context
import androidx.room.Room
import com.mihanovak1024.coronavirus.data.DataRepository
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.data.database.CoronaVirusDatabase
import com.mihanovak1024.coronavirus.data.network.CoronaVirusGithubDataSource
import com.mihanovak1024.coronavirus.data.network.CoronaVirusGithubDataSourceExtractor
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
    fun provideDataSource(webservice: CoronaVirusGithubDataWebservice, dataSourceExtractor: CoronaVirusGithubDataSourceExtractor): DataSource = CoronaVirusGithubDataSource(webservice, dataSourceExtractor)

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
    fun provideDatabase(applicationContext: Context): CoronaVirusDatabase = Room.databaseBuilder(
            applicationContext,
            CoronaVirusDatabase::class.java,
            "corona-database"
    ).build()

    @Singleton
    @Provides
    fun provideRepository(@Named("coronaVirusGithubDataSource") dataSource: DataSource, coronaVirusDatabase: CoronaVirusDatabase): Repository = DataRepository(dataSource, coronaVirusDatabase.timeSeriesCaseDataDao(), coronaVirusDatabase.timeSeriesCasePlaceDao())

    @Singleton
    @Provides
    fun provideDataSourceExtractor() = CoronaVirusGithubDataSourceExtractor()

}