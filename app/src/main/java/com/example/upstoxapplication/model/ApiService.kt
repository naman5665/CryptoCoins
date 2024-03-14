package com.example.upstoxapplication.model

import com.example.upstoxapplication.data.CryptoCurrencyResponse
import com.example.upstoxapplication.data.Cryptocurrencies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("ac7d7699-a7f7-488b-9386-90d1a60c4a4b")
    fun getCryptocurrencies(): Call<CryptoCurrencyResponse>
}