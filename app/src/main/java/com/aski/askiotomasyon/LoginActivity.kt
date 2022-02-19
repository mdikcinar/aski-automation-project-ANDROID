package com.aski.askiotomasyon

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var kullanici = FirebaseAuth.getInstance().currentUser

    override fun onStart() {
        super.onStart()
        if(kullanici != null){
            startActivity(Intent(this, HomeActivity::class.java))

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        tvKayitOl.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        })

        btnGirisyap.setOnClickListener {

            if(etEposta.text.isEmpty() && etSifre.text.isEmpty()){
                Toast.makeText(this, "Boş alanları doldurunuz!", Toast.LENGTH_LONG).show()
            }else{
                progressBarGoster()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etEposta.text.toString(), etSifre.text.toString())
                    .addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                        override fun onComplete(p0: Task<AuthResult>) {
                            if(p0.isSuccessful){
                                progressBarGizle()
                                Toast.makeText(this@LoginActivity, "Giriş başarılı!", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                                finish()
                            }else{
                                progressBarGizle()
                                Toast.makeText(this@LoginActivity, "Bir hata meydana geldi: "+ p0.exception?.message, Toast.LENGTH_LONG).show()
                            }
                        }

                    })
            }
        }


    }

    private fun progressBarGoster(){
        progressBarLogin.visibility = View.VISIBLE
    }
    private fun progressBarGizle(){
        progressBarLogin.visibility = View.INVISIBLE
    }
}
