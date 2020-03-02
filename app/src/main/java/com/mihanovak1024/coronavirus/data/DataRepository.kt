package com.mihanovak1024.coronavirus.data

import com.mihanovak1024.coronavirus.data.database.RoomDatabase
import com.mihanovak1024.coronavirus.data.network.DataSource

class DataRepository(
        private val dataSource: DataSource,
        private val roomDatabase: RoomDatabase
) : Repository {

    override suspend fun getTimeSeriesCaseAllData(): TimeSeriesCaseAllData {
        return dataSource.getTimeSeriesCaseAllData()
    }

}