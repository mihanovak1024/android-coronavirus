package com.mihanovak1024.coronavirus

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mihanovak1024.coronavirus.data.CoronaActivityViewModel
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.di.component.DaggerCoreComponent
import com.mihanovak1024.coronavirus.ui.LocationSwitchButton
import com.mihanovak1024.coronavirus.util.LOCAL_COUNTRY_CLICKED_SP
import com.mihanovak1024.coronavirus.util.LOCAL_COUNTRY_STRING_SP
import com.mihanovak1024.coronavirus.util.getDefaultSharedPreference
import kotlinx.android.synthetic.main.home_act.*
import javax.inject.Inject


class CoronaActivity : AppCompatActivity() {

    lateinit var coronaActivityViewModel: CoronaActivityViewModel

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)

        DaggerCoreComponent.builder()
                .appContext(applicationContext)
                .build()
                .inject(this)

        coronaActivityViewModel = ViewModelProvider(this).get(CoronaActivityViewModel::class.java)

        prepareViewPager()

        location_switch_button.setOnLocationSwitchClickCallback(
                object : LocationSwitchButton.OnLocationSwitchClickCallback {
                    override fun onLocalButtonClicked(context: Context, shouldOpenDialog: Boolean) {
                        updateViewModelCountryState()
                        if (shouldOpenDialog) {
                            openDialog()
                        }
                    }

                    override fun onWorldwideButtonClicked(context: Context) {
                        updateViewModelCountryState()
                    }
                }
        )

        updateViewModelCountryState()
    }

    private fun openDialog() {
        progress_bar.visibility = VISIBLE
        repository.getInfectedCountries().observe(this, Observer {
            progress_bar.visibility = GONE

            val builder = AlertDialog.Builder(this@CoronaActivity, R.style.LocationDialogTheme)
            builder.setItems(it.toTypedArray()) { dialog, which ->
                val sharedPreferences = getDefaultSharedPreference(baseContext).edit()
                sharedPreferences.putString(LOCAL_COUNTRY_STRING_SP, it[which])
                sharedPreferences.apply()

                location_switch_button.updateLocalSwitchCountryName(it[which])
                coronaActivityViewModel.country.value = it[which]

                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        })
    }

    private fun updateViewModelCountryState() {
        val sharedPreferences = getDefaultSharedPreference(baseContext)
        coronaActivityViewModel.country.value =
                if (sharedPreferences.getBoolean(LOCAL_COUNTRY_CLICKED_SP, false))
                    sharedPreferences.getString(LOCAL_COUNTRY_STRING_SP, "worldwide")
                else
                    "worldwide"
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
                            drawableId = R.drawable.ic_statistics; text = "Statistics"
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