package com.nick.app.covid19monitor.data.source.remote.response

class IndonesiaResponse : ArrayList<IndonesiaResponseItem>()

data class IndonesiaResponseItem(
    val meninggal: String,
    val name: String,
    val positif: String,
    val sembuh: String
)

data class GlobalPositif(
    val name: String,
    val value: String
)

data class GlobalSembuh(
    val name: String,
    val value: String
)

data class GlobalMeninggal(
    val name: String,
    val value: String
)