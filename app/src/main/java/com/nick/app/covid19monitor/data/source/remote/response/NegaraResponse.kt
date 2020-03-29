package com.nick.app.covid19monitor.data.source.remote.response

class NegaraResponse : ArrayList<NegaraResponseItem>()

data class NegaraResponseItem(
    val attributes: AttributesNegara
)

data class AttributesNegara(
    val Active: Int,
    val Confirmed: Int,
    val Country_Region: String,
    val Deaths: Int,
    val Last_Update: Long,
    val Lat: Double,
    val Long_: Double,
    val OBJECTID: Int,
    val Recovered: Int
)
