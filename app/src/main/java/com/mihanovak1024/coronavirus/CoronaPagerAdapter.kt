package com.mihanovak1024.coronavirus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mihanovak1024.coronavirus.home.HomeFragment
import com.mihanovak1024.coronavirus.info.InfoFragment
import com.mihanovak1024.coronavirus.map.MapFragment
import com.mihanovak1024.coronavirus.news.NewsFragment
import com.mihanovak1024.coronavirus.statistics.StatisticsFragment

class CoronaPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> NewsFragment()
            2 -> MapFragment()
            3 -> StatisticsFragment()
            4 -> InfoFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}