package com.aski.askiotomasyon

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.aski.askiotomasyon.model.Kullanici
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.notification.view.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tvZatenHesabimVar.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }

        btnKayitol.setOnClickListener {

            if(etEpostaKayit.text.isEmpty() && etAdSoyadKayit.text.isEmpty() && etTelnoKayit.text.isEmpty() && etSifreKayit.text.isEmpty() && etSifreTekrar.text.isEmpty() && etOnaykodu.text.isEmpty()) {
                Toast.makeText(this, "Hiçbir alan boş bırakılamaz", Toast.LENGTH_LONG).show()
            }else if (etTelnoKayit.text.length != 10){
                Toast.makeText(this, "Telefon numaranızı başında sıfır olmadan 10 haneli olarak giriniz!",Toast.LENGTH_LONG).show()
            }else if (etSifreKayit.text.length < 6){
                Toast.makeText(this, "Şifreniz 6 karakterden uzun olmalıdır!",Toast.LENGTH_LONG).show()
            }else if (!etSifreKayit.text.toString().equals(etSifreTekrar.text.toString())){
                Toast.makeText(this, "Girilen şifreler uyuşmuyor!",Toast.LENGTH_LONG).show()
            }else if (!etOnaykodu.text.toString().equals("123456")){
                Toast.makeText(this, "Onay kodunuz yanlış!",Toast.LENGTH_LONG).show()
            }else{

                kullaniciyiKaydet(etEpostaKayit.text.toString(), etAdSoyadKayit.text.toString(), etTelnoKayit.text.toString(), etSifreKayit.text.toString())

            }
        }
    }

    private fun kullaniciyiKaydet(eposta: String, adsoyad: String, telno: String, sifre: String) {
        progressBarGoster()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(eposta, sifre)
            .addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if(p0.isSuccessful){
                        var veriTabaninaEklenecekKullanici = Kullanici()
                        veriTabaninaEklenecekKullanici.kullaniciID = FirebaseAuth.getInstance().currentUser?.uid
                        veriTabaninaEklenecekKullanici.adSoyad = adsoyad
                        veriTabaninaEklenecekKullanici.telNo = telno
                        veriTabaninaEklenecekKullanici.seviye = "1"

                        FirebaseDatabase.getInstance().reference
                            .child("kullanicilar")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(veriTabaninaEklenecekKullanici).addOnCompleteListener { task ->

                                if(task.isSuccessful){
                                    progressBarGizle()
                                    Toast.makeText(this@RegisterActivity, "Yeni kullanıcı kaydı başarılı",Toast.LENGTH_LONG).show()
                                    FirebaseAuth.getInstance().signOut()
                                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                    finish()

                                }else{
                                    progressBarGizle()
                                    Toast.makeText(this@RegisterActivity, "Kayıt sırasında bir hata oluştu: "+ p0.exception?.message,Toast.LENGTH_LONG).show()
                                }
                            }

                    }else{
                        progressBarGizle()
                        Toast.makeText(this@RegisterActivity, "Kayıt sırasında bir hata oluştu: "+ p0.exception?.message,Toast.LENGTH_LONG).show()
                    }
                }

            })

    }

    private fun progressBarGoster(){
        progressBar.visibility = View.VISIBLE
    }
    private fun progressBarGizle(){
        progressBar.visibility = View.INVISIBLE
    }


}
