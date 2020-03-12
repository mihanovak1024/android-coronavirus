package com.mihanovak1024.coronavirus.ui

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.mihanovak1024.coronavirus.R
import com.mihanovak1024.coronavirus.util.LOCAL_COUNTRY_CLICKED_SP
import com.mihanovak1024.coronavirus.util.LOCAL_COUNTRY_STRING_SP
import com.mihanovak1024.coronavirus.util.getDefaultSharedPreference
import kotlinx.android.synthetic.main.location_switch_button.view.*


class LocationSwitchButton : LinearLayout {

    private var localButtonAlreadyClicked = false
    private var onLocationSwitchClickCallback: OnLocationSwitchClickCallback? = null

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initViews()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        initViews()
    }

    private fun initViews() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.location_switch_button, this, true)

        val sharedPreferences = getDefaultSharedPreference(context)
        val country = sharedPreferences.getString(LOCAL_COUNTRY_STRING_SP, "Local")
        if (country!!.isNotEmpty()) {
            updateLocalSwitchCountryName(country)
        }
        val countryClicked = sharedPreferences.getBoolean(LOCAL_COUNTRY_CLICKED_SP, false)
        if (countryClicked) {
            localButtonClicked()
        } else {
            worldwideButtonClicked()
        }

        local_button.setOnClickListener {
            localButtonClicked()
        }
        worldwide_button.setOnClickListener {
            worldwideButtonClicked()
        }

    }

    private fun worldwideButtonClicked() {
        if (localButtonAlreadyClicked) {
            localButtonAlreadyClicked = false
            switchLocalWorldwideButtonsStyle(worldwide_button, local_button)
            updateSharedPreferencesClick(false)
            onLocationSwitchClickCallback?.onWorldwideButtonClicked(context)
        }
    }

    private fun localButtonClicked() {
        onLocationSwitchClickCallback?.onLocalButtonClicked(context, !localButtonAlreadyClicked)
        if (!localButtonAlreadyClicked) {
            localButtonAlreadyClicked = true
            switchLocalWorldwideButtonsStyle(local_button, worldwide_button)
            updateSharedPreferencesClick(true)
        }
    }

    private fun updateSharedPreferencesClick(countryClicked: Boolean) {
        val sharedPreferencesEditor = getDefaultSharedPreference(context).edit()
        sharedPreferencesEditor.putBoolean(LOCAL_COUNTRY_CLICKED_SP, countryClicked)
        sharedPreferencesEditor.apply()
    }

    private fun switchLocalWorldwideButtonsStyle(enabledView: TextView, disabledView: TextView) {
        enabledView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        enabledView.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorAccent, null))
        enabledView.alpha = 1f
        enabledView.layoutParams = (enabledView.layoutParams as LayoutParams).apply {
            weight = 2f
        }
        disabledView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        disabledView.setBackgroundColor(ResourcesCompat.getColor(resources, android.R.color.darker_gray, null))
        disabledView.alpha = 0.8f
        disabledView.layoutParams = (disabledView.layoutParams as LayoutParams).apply {
            weight = 1f
        }
    }

    fun updateLocalSwitchCountryName(countryName: String) {
        local_button.text = countryName
    }

    fun setOnLocationSwitchClickCallback(onLocationSwitchClickCallback: OnLocationSwitchClickCallback) {
        this.onLocationSwitchClickCallback = onLocationSwitchClickCallback
    }

    interface OnLocationSwitchClickCallback {
        fun onLocalButtonClicked(context: Context, firstTime: Boolean)
        fun onWorldwideButtonClicked(context: Context)
    }
}