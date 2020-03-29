package com.nick.app.covid19monitor.utils

class Constans {
    companion object {
        const val API_PROVINSI =
            "?where=1%3D1&outFields=Provinsi,Kasus_Posi,Kasus_Semb,Kasus_Meni&returnGeometry=false&orderByFields=Provinsi%20ASC&outSR=&f=json"
        const val API_NEGARA = "."
        const val API_INDONESIA = "indonesia"
        const val API_GLOBAL_MENINGGAL = "meninggal"
        const val API_GLOBAL_SEMBUH = "sembuh"
        const val API_GLOBAL_POSITIF = "positif"
    }
}