package com.lukasz.cointracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukasz.cointracker.network.Asset
import com.lukasz.cointracker.network.MessariApi
import kotlinx.coroutines.*

class OverviewViewModel :ViewModel(){

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
    get() = _response

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getAssets()
    }
    private fun getAssets(){
        coroutineScope.launch {
            val getAssetsDeferred = MessariApi.retrofitService.getAssets()
            try {
                val result =  getAssetsDeferred.await()
                _response.value = "Success: ${result.data.size} assets retrieved"
            } catch (e: Exception){
                _response.value = "Failure: ${e.message}"
            }
        }
    }

}