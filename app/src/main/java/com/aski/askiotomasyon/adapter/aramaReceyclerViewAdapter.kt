package com.aski.askiotomasyon.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aski.askiotomasyon.R
import com.aski.askiotomasyon.TesisActivity
import kotlinx.android.synthetic.main.tesis_list_item.view.*

class aramaReceyclerViewAdapter(context: Context, tumTesisAdlari:ArrayList<String?>, arizaliTesisAdlari:ArrayList<String?>, tumTesisMesafeleri:ArrayList<String?>):

    RecyclerView.Adapter<aramaReceyclerViewAdapter.homeViewHolder>() {

    var myContext = context //gelen contexi my contex'e ata
    var mytumTesisAdlari = tumTesisAdlari //gelen string diziyi mydiziye ata
    var myarizaliTesisAdlari = arizaliTesisAdlari
    var mytumTesisMesafeleri = tumTesisMesafeleri

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeViewHolder { //parent içine koyulan recyclerview'i temsil ediyor
        //inflator ile layout'u xml'den javaya dönüştürüyoruz
        var infilator = LayoutInflater.from(myContext)
        var view = infilator.inflate(R.layout.tesis_list_item, parent, false)  //layoutu' parrent'ın içine koy

        return homeViewHolder(view) //dönüştürülen layout'u viewholder'a gönder
    }

    //@SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: homeViewHolder, position: Int) {

        holder?.tesisAdi.setTextColor(ContextCompat.getColor(myContext, R.color.colorHint))
        holder?.ivTesisResmi.setColorFilter(ContextCompat.getColor(myContext, R.color.colorHint))
        //holder?.tumLayout.ivListItemEkleSil.setColorFilter(ContextCompat.getColor(myContext, R.color.colorHint))
        //holder?.tumLayout.tvListeItemMesafe.text = mytumTesisMesafeleri.get(position)
        holder?.tumLayout.tvListeItemMesafe.text = "3 km"

        for(tesis in myarizaliTesisAdlari){ //eğer tesis arızalı tesis adları içerisinde varsa adını kırımızı yap
           if (tesis.equals(mytumTesisAdlari.get(position))){
               holder?.tesisAdi.setTextColor(ContextCompat.getColor(myContext, R.color.colorRed))
               holder?.ivTesisResmi.setColorFilter(ContextCompat.getColor(myContext, R.color.colorRed))
           }
        }
        holder?.tesisAdi.text = mytumTesisAdlari.get(position)


        holder?.tumLayout.setOnClickListener {
            var intent = Intent(myContext, TesisActivity::class.java)
            intent.putExtra("tiklananTesis", mytumTesisAdlari.get(position))
            myContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mytumTesisAdlari.size //listemdeki eleman sayısını belirtiyorum
    }


    inner class homeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){ //gelen layout'a verileri burada yerleştiriyoruz

        var tumLayout = itemView as ConstraintLayout //gelen genel View türünü typecasting yaparak hangi tür olduğunu belirtiyoruz.

        var tesisAdi = tumLayout.tvListItemTesisAdi
        var ivTesisResmi = tumLayout.ivListItemTesisResmi


    }

}