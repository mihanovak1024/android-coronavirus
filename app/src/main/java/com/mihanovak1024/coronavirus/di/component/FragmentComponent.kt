package com.mihanovak1024.coronavirus.di.component

import android.content.Context
import com.mihanovak1024.coronavirus.di.module.ViewModelModule
import com.mihanovak1024.coronavirus.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ViewModelModule::class
        ]
)
interface FragmentComponent {

    fun inject(target: HomeFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(applicationContext: Context): Builder

        fun build(): FragmentComponent
    }

}