package com.mihanovak1024.coronavirus.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.mihanovak1024.coronavirus.databinding.HomeFragmentBinding
import com.mihanovak1024.coronavirus.di.component.DaggerFragmentComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        DaggerFragmentComponent.builder().build().inject(this)

        val homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
                .apply {
                    homeViewModel = homeFragmentViewModel
                }

        homeFragmentViewModel.viewModelScope.launch {
            homeFragmentViewModel.updateNumberStatistics()
        }

        homeFragmentBinding.lifecycleOwner = this

        return homeFragmentBinding.root
    }

}