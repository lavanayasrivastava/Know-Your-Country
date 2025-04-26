package com.example.knowyourcountry


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class mainViewModel : ViewModel(){

    data class dataBase(
        val loading: Boolean = true,
        val error : String? = null,
        val responseList: List<keyValue> = emptyList()
    )

     private val _dbObj = mutableStateOf(dataBase())
     val dbObj : MutableState<dataBase> = _dbObj
     //val countryName = mutableStateOf("")


    fun fetchData(countryName: String) {
        _dbObj.value = dataBase(loading = true)
        viewModelScope.launch {
            try {
                val response = apiObj.getCountryData(countryName)
                _dbObj.value = _dbObj.value.copy(
                   loading = false,
                   responseList = listOf(response.data)
               )
            }
            catch (e: Exception) {
                _dbObj.value = _dbObj.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }
}
