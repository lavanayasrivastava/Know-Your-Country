package com.example.knowyourcountry

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

val retrofitObj: Retrofit = Retrofit.Builder().baseUrl("https://countries-api-abhishek.vercel.app/").
        addConverterFactory(GsonConverterFactory.create()).build()

val apiObj : apiService = retrofitObj.create(apiService::class.java)

interface apiService {

    @GET("countries/{country}")
    suspend fun getCountryData(
        @Path("country") name: String
    ) : Response
}
