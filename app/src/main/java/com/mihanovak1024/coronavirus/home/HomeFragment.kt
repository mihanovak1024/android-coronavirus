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

        activity?.let { fragmentActivity ->
            val coronaActivityViewModel = ViewModelProvider(fragmentActivity).get(CoronaActivityViewModel::class.java)
            coronaActivityViewModel.country.observe(viewLifecycleOwner, Observer {
                homeFragmentViewModel.country.value = it
            })
        }

        val homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
                .apply {
                    homeViewModel = homeFragmentViewModel
                }

        homeFragmentBinding.lifecycleOwner = this

        return homeFragmentBinding.root
    }

}