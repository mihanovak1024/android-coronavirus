package com.mihanovak1024.coronavirus.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.mihanovak1024.coronavirus.data.network.RetrofitDataSource
import com.mihanovak1024.coronavirus.databinding.HomeFragmentBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val retrofitDataSource = RetrofitDataSource()
        val homeFragmentViewModel = HomeViewModel(retrofitDataSource)

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