package com.aski.askiotomasyon.model

class TesisVeri {

    var tesisID: String? = null
    var tesisAdi: String? = null
    var tesisDurumu: String? = null
    var hamsuDepoSeviye: String? = null
    var temizsuDepoSeviye: String? = null
    var kuyuMotorDurum: String? = null
    var aritmaMotor0Durum: String? = null
    var aritmaMotor1Durum: String? = null
    var aritmaMotor2Durum: String? = null
    var tesisDurumuKalanSure: String? = null
    var tesisKonumu: String? = null
    var arizaDurumu: String? = null

    constructor()

    constructor(
        tesisID: String?,
        tesisAdi: String?,
        tesisDurumu: String?,
        hamsuDepoSeviye: String?,
        temizsuDepoSeviye: String?,
        kuyuMotorDurum: String?,
        aritmaMotor0Durum: String?,
        aritmaMotor1Durum: String?,
        aritmaMotor2Durum: String?,
        tesisDurumuKalanSure: String?,
        tesisKonumu: String?,
        arizaDurumu: String?
    ) {
        this.tesisID = tesisID
        this.tesisAdi = tesisAdi
        this.tesisDurumu = tesisDurumu
        this.hamsuDepoSeviye = hamsuDepoSeviye
        this.temizsuDepoSeviye = temizsuDepoSeviye
        this.kuyuMotorDurum = kuyuMotorDurum
        this.aritmaMotor0Durum = aritmaMotor0Durum
        this.aritmaMotor1Durum = aritmaMotor1Durum
        this.aritmaMotor2Durum = aritmaMotor2Durum
        this.tesisDurumuKalanSure = tesisDurumuKalanSure
        this.tesisKonumu = tesisKonumu
        this.arizaDurumu = arizaDurumu
    }


}