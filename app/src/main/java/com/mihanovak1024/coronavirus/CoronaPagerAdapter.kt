package com.mihanovak1024.coronavirus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mihanovak1024.coronavirus.home.HomeFragment
import com.mihanovak1024.coronavirus.statistics.StatisticsFragment

class CoronaPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> StatisticsFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}