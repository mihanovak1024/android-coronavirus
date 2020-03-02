package com.mihanovak1024.coronavirus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.home_act.*


class CoronaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)



        prepareViewPager()
    }

    private fun prepareViewPager() {
        val viewPager = view_pager
        val tabLayout = tab_layout
        viewPager.adapter = CoronaPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager,
                TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                    val text: String
                    val drawableId: Int
                    when (position) {
                        1 -> {
                            drawableId = R.drawable.ic_news
                            text = "News"
                        }
                        2 -> {
                            drawableId = R.drawable.ic_map
                            text = "Map"
                        }
                        3 -> {
                            drawableId = R.drawable.ic_statistics
                            text = "Statistics"
                        }
                        4 -> {
                            drawableId = R.drawable.ic_info
                            text = "Info"
                        }
                        else -> {
                            drawableId = R.drawable.ic_home
                            text = "Home"
                        }
                    }
                    tab.icon = ResourcesCompat.getDrawable(resources, drawableId, null)
                    tab.text = text
                }
        ).attach()

    }
}