package com.aski.askiotomasyon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aski.askiotomasyon.model.TesisVeri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_tesis.*

class TesisActivity : AppCompatActivity() {


    lateinit var tiklananTesis:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tesis)

        var intent = getIntent()
        tiklananTesis = intent.getStringExtra("tiklananTesis")
        txtTesisAdi.text = tiklananTesis

        tesisVerileriniOku()

        btnKuyuDetaylari.setOnClickListener {

            var dialogGoster = kuyuBilgileriFragment()
            dialogGoster.show(supportFragmentManager, "goster")
        }

        btnAritmaDetaylari.setOnClickListener {

            var dialogGoster2 = aritmaBilgileriFragment()
            dialogGoster2.show(supportFragmentManager, "goster2")
        }


        linHaritadaGoster.setOnClickListener {

            //val gmmIntentUri: Uri = Uri.parse("google.navigation:q=37.859152,27.800992") // yol tarifi al
            val gmmIntentUri: Uri = Uri.parse("geo:0,0?q=37.859152,27.800992") // bu veri firebaseden ├žekilecek
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }
    }

    private fun tesisVerileriniOku(){

        var ref= FirebaseDatabase.getInstance().reference

        var sorgu=ref.child("Tesis Verileri").child(tiklananTesis).addValueEventListener(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {


                var okunanTesis = p0.getValue(TesisVeri::class.java)

                if(okunanTesis?.tesisAdi!=null)
                    txtTesisAdi.setTextColor(ContextCompat.getColor(baseContext,R.color.colorHint))

                if(!okunanTesis?.tesisDurumu.equals("Bilinmiyor")) {

                    txtTesisDurum.text = okunanTesis?.tesisDurumu
                    if (okunanTesis?.tesisDurumu.equals("Tesis ├çal─▒┼čm─▒yor")) {

                        txtTesisAdi.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                        txtTesisDurum.setTextColor(ContextCompat.getColor(baseContext, R.color.colorRed))
                    }
                    else if (okunanTesis?.tesisDurumu.equals("Ar─▒tma Yap─▒l─▒yor")) {
                        txtTesisAdi.setTextColor(ContextCompat.getColor(baseContext,R.color.colorHint))
                        txtTesisDurum.setTextColor(ContextCompat.getColor(baseContext, R.color.colorGreen))
                    }
                    else if (okunanTesis?.tesisDurumu.equals("Ters Y─▒kama Yap─▒l─▒yor")) {
                        txtTesisAdi.setTextColor(ContextCompat.getColor(baseContext,R.color.colorHint))
                        txtTesisDurum.setTextColor(ContextCompat.getColor(baseContext, R.color.colorYellow))
                    }
                    else if (okunanTesis?.tesisDurumu.equals("Durulama Yap─▒l─▒yor")) {
                        txtTesisAdi.setTextColor(ContextCompat.getColor(baseContext,R.color.colorHint))
                        txtTesisDurum.setTextColor(ContextCompat.getColor(baseContext, R.color.colorYellow))
                    }
                    else {
                        txtTesisAdi.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                        txtTesisDurum.setTextColor(ContextCompat.getColor(baseContext, R.color.colorRed))
                    }

                    txtTesisHamsuSeviye.setText(okunanTesis?.hamsuDepoSeviye)

                    if(okunanTesis?.hamsuDepoSeviye != null && okunanTesis?.temizsuDepoSeviye != null) {

                        if (Integer.valueOf(okunanTesis?.hamsuDepoSeviye!!) < 20)
                            txtTesisHamsuSeviye.setTextColor(
                                ContextCompat.getColor(
                                    baseContext,
                                    R.color.colorRed
                                )
                            )
                        if (Integer.valueOf(okunanTesis?.hamsuDepoSeviye!!) > 80)
                            txtTesisHamsuSeviye.setTextColor(
                                ContextCompat.getColor(
                                    baseContext,
                                    R.color.colorGreen
                                )
                            )
                        else txtTesisHamsuSeviye.setTextColor(
                            ContextCompat.getColor(
                                baseContext,
                                R.color.colorHint
                            )
                        )
                        txtTesisTemizsuSeviye.setText(okunanTesis?.temizsuDepoSeviye)
                        if (Integer.valueOf(okunanTesis?.temizsuDepoSeviye!!) < 20)
                            txtTesisTemizsuSeviye.setTextColor(
                                ContextCompat.getColor(
                                    baseContext,
                                    R.color.colorRed
                                )
                            )
                        if (Integer.valueOf(okunanTesis?.temizsuDepoSeviye!!) > 80)
                            txtTesisTemizsuSeviye.setTextColor(
                                ContextCompat.getColor(
                                    baseContext,
                                    R.color.colorGreen
                                )
                            )
                        else txtTesisTemizsuSeviye.setTextColor(
                            ContextCompat.getColor(
                                baseContext,
                                R.color.colorHint
                            )
                        )
                    }

                    txtdurumKalanSure.setText(okunanTesis?.tesisDurumuKalanSure)
                    txtTesisMotor0Durum.setText(okunanTesis?.aritmaMotor0Durum)
                    if (okunanTesis?.aritmaMotor0Durum.equals("Ar─▒zal─▒"))
                        txtTesisMotor0Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                    else if (okunanTesis?.aritmaMotor0Durum.equals("├çal─▒┼č─▒yor"))
                        txtTesisMotor0Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorGreen))
                    else if (okunanTesis?.aritmaMotor0Durum.equals("Haz─▒r"))
                        txtTesisMotor0Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorYellow))
                    else
                        txtTesisMotor0Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                    txtTesisMotor1Durum.setText(okunanTesis?.aritmaMotor1Durum)
                    if (okunanTesis?.aritmaMotor1Durum.equals("Ar─▒zal─▒"))
                        txtTesisMotor1Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                    else if (okunanTesis?.aritmaMotor1Durum.equals("├çal─▒┼č─▒yor"))
                        txtTesisMotor1Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorGreen))
                    else if (okunanTesis?.aritmaMotor1Durum.equals("Haz─▒r"))
                        txtTesisMotor1Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorYellow))
                    else
                        txtTesisMotor1Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                    txtTesisMotor2Durum.setText(okunanTesis?.aritmaMotor2Durum)
                    if (okunanTesis?.aritmaMotor2Durum.equals("Ar─▒zal─▒"))
                        txtTesisMotor2Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                    else if (okunanTesis?.aritmaMotor2Durum.equals("├çal─▒┼č─▒yor"))
                        txtTesisMotor2Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorGreen))
                    else if (okunanTesis?.aritmaMotor2Durum.equals("Haz─▒r"))
                        txtTesisMotor2Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorYellow))
                    else
                        txtTesisMotor2Durum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                    txtTesisKuyuMotorDurum.setText(okunanTesis?.kuyuMotorDurum)
                    if (okunanTesis?.kuyuMotorDurum.equals("Ar─▒zal─▒"))
                        txtTesisKuyuMotorDurum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                    else if (okunanTesis?.kuyuMotorDurum.equals("├çal─▒┼č─▒yor"))
                        txtTesisKuyuMotorDurum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorGreen))
                    else if (okunanTesis?.kuyuMotorDurum.equals("Haz─▒r"))
                        txtTesisKuyuMotorDurum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorYellow))
                    else txtTesisKuyuMotorDurum.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                }else{

                    //tesis durumu bilinmiyor ise bunlar yap─▒lacak.
                    txtTesisAdi.setTextColor(ContextCompat.getColor(baseContext,R.color.colorRed))
                }


            }
            //homeAdapter.notifyDataSetChanged()


        })
    }
}
