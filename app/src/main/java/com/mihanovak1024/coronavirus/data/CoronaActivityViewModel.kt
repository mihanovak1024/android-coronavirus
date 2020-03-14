package com.mihanovak1024.coronavirus.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class CoronaActivityViewModel : ViewModel() {

    var country: MutableLiveData<String> = MutableLiveData("worldwide")

}