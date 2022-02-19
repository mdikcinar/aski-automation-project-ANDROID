package com.aski.askiotomasyon.model

class Tesis {
    var tesisAdi: String? = null
    var arizaDurumu: String? = null

    constructor()

    constructor(tesisAdi: String?, arizaDurumu: String?) {
        this.tesisAdi = tesisAdi
        this.arizaDurumu = arizaDurumu
    }


}