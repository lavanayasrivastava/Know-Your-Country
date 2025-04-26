package com.example.knowyourcountry

data class keyValue(
    val name: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val population: Long,
    val area: Long,
    val coordinates: Coordinates,
    val borders: List<String>,
    val timezones: List<String>,
    val currency: String,
    val languages: List<String>,
    val flag: String
)

data class Response(
    val message: String,
    val data: keyValue,
    val statusCode: Int
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

