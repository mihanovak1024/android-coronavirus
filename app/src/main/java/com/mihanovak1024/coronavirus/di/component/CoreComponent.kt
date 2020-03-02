package com.mihanovak1024.coronavirus.di.component

import com.mihanovak1024.coronavirus.CoronaActivity
import com.mihanovak1024.coronavirus.di.module.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            DataModule::class
        ]
)
interface CoreComponent {

    fun inject(target: CoronaActivity)

}