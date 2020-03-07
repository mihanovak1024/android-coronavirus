package com.mihanovak1024.coronavirus.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mihanovak1024.coronavirus.databinding.HomeFragmentBinding
import com.mihanovak1024.coronavirus.di.component.DaggerFragmentComponent
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        DaggerFragmentComponent.builder().appContext(requireActivity().applicationContext).build().inject(this)

        val homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
                .apply {
                    homeViewModel = homeFragmentViewModel
                }

        homeFragmentBinding.lifecycleOwner = this

        return homeFragmentBinding.root
    }

}