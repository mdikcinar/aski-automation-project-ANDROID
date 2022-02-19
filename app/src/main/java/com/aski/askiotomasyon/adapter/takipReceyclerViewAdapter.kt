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

class takipReceyclerViewAdapter(context: Context, arizaliTesisAdlari:ArrayList<String?>):

    RecyclerView.Adapter<takipReceyclerViewAdapter.homeViewHolder>() {

    var myContext = context //gelen contexi my contex'e ata
    var myarizaliTesisAdlari = arizaliTesisAdlari //gelen string diziyi mydiziye ata

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeViewHolder { //parent içine koyulan recyclerview'i temsil ediyor
        //inflator ile layout'u xml'den javaya dönüştürüyoruz
        var infilator = LayoutInflater.from(myContext)
        var view = infilator.inflate(R.layout.tesis_list_item, parent, false)  //layoutu' parrent'ın içine koy

        return homeViewHolder(view) //dönüştürülen layout'u viewholder'a gönder
    }

    //@SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: homeViewHolder, position: Int) {


        holder?.tesisAdi.text = myarizaliTesisAdlari.get(position)
        holder?.tesisAdi.setTextColor(ContextCompat.getColor(myContext, R.color.colorRed))
        holder?.ivTesisResmi.setColorFilter(ContextCompat.getColor(myContext, R.color.colorRed))

        holder?.tumLayout.setOnClickListener {
            var intent = Intent(myContext, TesisActivity::class.java)
            intent.putExtra("tiklananTesis", myarizaliTesisAdlari.get(position))
            myContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return myarizaliTesisAdlari.size //listemdeki eleman sayısını belirtiyorum
    }


    inner class homeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){ //gelen layout'a verileri burada yerleştiriyoruz

        var tumLayout = itemView as ConstraintLayout //gelen genel View türünü typecasting yaparak hangi tür olduğunu belirtiyoruz.

        var tesisAdi = tumLayout.tvListItemTesisAdi
        var ivTesisResmi = tumLayout.ivListItemTesisResmi


    }

}