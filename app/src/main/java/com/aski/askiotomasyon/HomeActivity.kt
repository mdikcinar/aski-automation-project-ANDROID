package com.aski.askiotomasyon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aski.askiotomasyon.adapter.aramaReceyclerViewAdapter
import com.aski.askiotomasyon.adapter.homeReceyclerViewAdapter
import com.aski.askiotomasyon.adapter.takipReceyclerViewAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

//data class konumBilgisi(var latitute:Double, var longitute:Double)

class HomeActivity : AppCompatActivity() {

    var arizaliTesisAdlari = ArrayList<String?>()
    var tumTesisAdlari = ArrayList<String?>()
    var tumTesisMesafeleri = ArrayList<String?>()

    var takipEdilenTesisAdlari = ArrayList<String?>()
    var homeAdapter = homeReceyclerViewAdapter(this, arizaliTesisAdlari,tumTesisMesafeleri)
    var aramaAdapter = aramaReceyclerViewAdapter(this, tumTesisAdlari, arizaliTesisAdlari,tumTesisMesafeleri)
    var takipAdapter = takipReceyclerViewAdapter(this, takipEdilenTesisAdlari)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
        setupBottomNavigationView()
        TesisleriListele()
        rvHome.adapter = homeAdapter
        rvHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }


    private fun TesisleriListele() {

        var ref = FirebaseDatabase.getInstance().reference

        var sorgu = ref.child("Tesisler").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                arizaliTesisAdlari.clear()
                takipEdilenTesisAdlari.clear()
                tumTesisAdlari.clear()

                for (birTesis in p0.children) {

                    tumTesisAdlari.add(birTesis.key)


                    if (birTesis.child("takipEdenler")
                            .hasChild(FirebaseAuth.getInstance().uid!!)
                    ) { //takipEdenler'de kişinin uid'si varsa tesisi takip edilenlere ekle
                        takipEdilenTesisAdlari.add(birTesis.key)
                    }

                    if (birTesis.child("arizaDurumu").value.toString()
                            .equals("1")
                    ) { //tesis arızalı ise arızalılara ekle
                        arizaliTesisAdlari.add(birTesis.key)
                    }
                    //getLocation() //buraya depoların konumları gönderilcek
                    adapterleriYenile()
                }
            }

        })
    }

    private fun setupBottomNavigationView() {

        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navHome -> {
                    rvHome.adapter = homeAdapter
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navSearch -> {
                    rvHome.adapter = aramaAdapter
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navList -> {
                    rvHome.adapter = takipAdapter
                    return@setOnNavigationItemSelectedListener true
                }


            }
            return@setOnNavigationItemSelectedListener false

        }

    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun adapterleriYenile(){
        homeAdapter.notifyDataSetChanged()
        aramaAdapter.notifyDataSetChanged()
        takipAdapter.notifyDataSetChanged()
    }

    private fun getLocation() {

        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        var locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location?) {
                tumTesisMesafeleri.clear()
                var latitute = location!!.latitude
                var longitute = location!!.longitude
                Log.i("mustafa", "NormalLatitute: $latitute ; NormalLongitute: $longitute")
                for(birTesis in tumTesisAdlari) {
                    tumTesisMesafeleri.add(mesafeHesapla(latitute, longitute))
                }
                adapterleriYenile()
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

        }

        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
                return
            }
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 5f, locationListener)
        } catch (ex: SecurityException) {
            Toast.makeText(applicationContext, "Bir hata meydana geldi!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLocation()
                PackageManager.PERMISSION_DENIED -> Toast.makeText(this,"Hata izine ihtiyaç var", Toast.LENGTH_LONG)
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }

    private fun mesafeHesapla(latitute:Double, langitute:Double):String{

        val l1 = Location("One")
        l1.setLatitude(latitute)
        l1.setLongitude(langitute)

        val l2 = Location("Two")
        val depoLatitude = 37.859152
        val depoLangitute = 27.800992
        l2.setLatitude(depoLatitude)
        l2.setLongitude(depoLangitute)

        var aradakiMesafe: Float = l1.distanceTo(l2)
        Log.i("mustafa", "Latitute: $latitute ; Longitute: $langitute")
        Log.i("mustafa", "DepoLatitute: $depoLatitude ; DepoLongitute: $depoLangitute")
        Log.i("mustafa", "Mesafe: " + aradakiMesafe)

        if(aradakiMesafe > 1000){
            aradakiMesafe = aradakiMesafe / 1000
            return "%.1f".format(aradakiMesafe).toString()+" km"
        }
        return "%.0f".format(aradakiMesafe).toString()+" m"

    }

}



