package com.mihanovak1024.coronavirus

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.di.component.DaggerCoreComponent
import com.mihanovak1024.coronavirus.ui.LocationSwitchButton
import com.mihanovak1024.coronavirus.util.LOCAL_COUNTRY_STRING_SP
import com.mihanovak1024.coronavirus.util.getDefaultSharedPreference
import kotlinx.android.synthetic.main.home_act.*
import javax.inject.Inject


class CoronaActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)

        DaggerCoreComponent.builder()
                .appContext(applicationContext)
                .build()
                .inject(this)

        prepareViewPager()

        location_switch_button.setOnLocalButtonClickCallback(
                object : LocationSwitchButton.OnLocalButtonClickCallback {
                    override fun onLocalButtonClicked(context: Context) {
                        openDialog()
                    }
                }
        )
    }

    private fun openDialog() {
        progress_bar.visibility = VISIBLE
        repository.getInfectedLocations().observe(this, Observer {
            progress_bar.visibility = GONE

            val builder = AlertDialog.Builder(this@CoronaActivity, R.style.LocationDialogTheme)
            builder.setItems(it.toTypedArray()) { dialog, which ->
                // todo Create MVP logic
                val sharedPreferences = getDefaultSharedPreference(baseContext).edit()
                sharedPreferences.putString(LOCAL_COUNTRY_STRING_SP, it[which])
                sharedPreferences.apply()
                location_switch_button.updateLocalSwitchCountryName(it[which])
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        })
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
                            drawableId = R.drawable.ic_news; text = "News"
                        }
                        2 -> {
                            drawableId = R.drawable.ic_map; text = "Map"
                        }
                        3 -> {
                            drawableId = R.drawable.ic_statistics; text = "Statistics"
                        }
                        4 -> {
                            drawableId = R.drawable.ic_info; text = "Info"
                        }
                        else -> {
                            drawableId = R.drawable.ic_home; text = "Home"
                        }
                    }
                    tab.icon = ResourcesCompat.getDrawable(resources, drawableId, null)
                    tab.text = text
                }
        ).attach()

    }
}