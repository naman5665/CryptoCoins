package com.example.upstoxapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upstoxapplication.data.Cryptocurrencies
import com.example.upstoxapplication.repository.CryptocurrenciesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoCurrencyViewModel: ViewModel() {

    private val repository = CryptocurrenciesRepository()

    private val _cryptocurrencies = MutableLiveData<List<Cryptocurrencies>>()
    val cryptocurrencies: LiveData<List<Cryptocurrencies>>
        get() = _cryptocurrencies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchCryptocurrencies() {
        _isLoading.value = true
        repository.getCryptocurrencies { cryptocurrencies, errorMessage ->
            _isLoading.value = false
            if (cryptocurrencies != null) {
                _cryptocurrencies.postValue(cryptocurrencies)
            } else {
                _errorMessage.postValue(errorMessage)
            }
        }
    }
}