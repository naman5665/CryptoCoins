package com.example.upstoxapplication.repository

import android.widget.Toast
import com.example.upstoxapplication.data.CryptoCurrencyResponse
import com.example.upstoxapplication.data.Cryptocurrencies
import com.example.upstoxapplication.model.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class CryptocurrenciesRepository {

    fun getCryptocurrencies(callback: (List<Cryptocurrencies>?, String?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getCryptocurrencies()

        call.enqueue(object : Callback<CryptoCurrencyResponse> {
            override fun onResponse(
                call: Call<CryptoCurrencyResponse>,
                response: Response<CryptoCurrencyResponse>
            ) {
                if (response.isSuccessful) {
                    val cryptocurrencyResponse = response.body()
                    cryptocurrencyResponse.let {
                        val cryptocurrencies = it?.cryptocurrencies
                        callback(cryptocurrencies, null)
                    }

                } else {
                    val errorMessage = response.message()
                    callback(null, errorMessage)
                }
            }

            override fun onFailure(call: Call<CryptoCurrencyResponse>, t: Throwable) {
                callback(null, t.message)
            }
        })
    }
}