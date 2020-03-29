package com.nick.app.covid19monitor.data.source.remote.response


data class ProvinsiResponse(
    val features: List<Feature>
)

data class Feature(
    val attributes: Attributes
)

data class Attributes(
    val Kasus_Meni: Int,
    val Kasus_Posi: Int,
    val Kasus_Semb: Int,
    val Provinsi: String
)

