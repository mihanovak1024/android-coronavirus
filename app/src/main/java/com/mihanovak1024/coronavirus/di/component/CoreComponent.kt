package com.mihanovak1024.coronavirus.di.component

import android.content.Context
import com.mihanovak1024.coronavirus.CoronaActivity
import com.mihanovak1024.coronavirus.di.module.DataModule
import dagger.BindsInstance
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

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(applicationContext: Context): Builder

        fun build(): CoreComponent
    }

}