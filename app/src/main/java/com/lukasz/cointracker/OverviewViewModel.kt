package com.lukasz.cointracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OverviewViewModel :ViewModel(){

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
    get() = _text

}