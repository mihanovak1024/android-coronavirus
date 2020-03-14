package com.mihanovak1024.coronavirus.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mihanovak1024.coronavirus.data.CoronaActivityViewModel
import com.mihanovak1024.coronavirus.databinding.HomeFragmentBinding
import com.mihanovak1024.coronavirus.di.component.DaggerFragmentComponent
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        DaggerFragmentComponent.builder()
                .appContext(requireActivity().applicationContext)
                .build()
                .inject(this)

        val homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
                .apply {
                    homeViewModel = homeFragmentViewModel
                    lifecycleOwner = this@HomeFragment
                }


        setObservers()

        return homeFragmentBinding.root
    }

    private fun setObservers() {
        observeActivityCountry()
        observeDailyStatistics()
    }

    private fun observeActivityCountry() {
        activity?.let { fragmentActivity ->
            val coronaActivityViewModel = ViewModelProvider(fragmentActivity).get(CoronaActivityViewModel::class.java)
            coronaActivityViewModel.country.observe(viewLifecycleOwner, Observer {
                homeFragmentViewModel.country.value = it
            })
        }
    }

    private fun observeDailyStatistics() {
        homeFragmentViewModel.numberDailyStatistics.observe(viewLifecycleOwner, Observer { timeSeriesDataList ->
            var infected = 0
            var recovered = 0
            var deaths = 0
            timeSeriesDataList.forEach {
                infected += it.infectedCases
                recovered += it.recoveredCases
                deaths += it.deathCases
            }
            homeFragmentViewModel.infected.value = "$infected"
            homeFragmentViewModel.recovered.value = "$recovered"
            homeFragmentViewModel.deaths.value = "$deaths"
        })
    }

}