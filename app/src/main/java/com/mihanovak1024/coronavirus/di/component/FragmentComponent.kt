package com.mihanovak1024.coronavirus.di.component

import com.mihanovak1024.coronavirus.di.module.ViewModelModule
import com.mihanovak1024.coronavirus.home.HomeFragment
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

}