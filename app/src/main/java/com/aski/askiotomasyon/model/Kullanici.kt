package com.aski.askiotomasyon.model

class Kullanici {

    var kullaniciID: String? = null
    var adSoyad: String? = null
    var telNo: String? = null
    var seviye: String? = null

    constructor(kullaniciID: String, adSoyad: String, telNo: String, seviye: String){
        this.kullaniciID = kullaniciID
        this.adSoyad = adSoyad
        this.telNo = telNo
        this.seviye = seviye
    }

    constructor() {}

}