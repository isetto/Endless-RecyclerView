package com.example.qpony

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.qpony.Network.Model.Currencies
import java.text.SimpleDateFormat



class RecyclerViewMain(private val currencyList: MutableList<Currencies>, private val context: Context?) : RecyclerView.Adapter<RecyclerViewMain.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  //parent - kontener z informacjami. Layoutinflater laczy kontener(parent)
            : MyViewHolder
    // z userslist
    { // view podstawowa klasa po ktorej dziedzicza kontrolki
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_view_main, parent, false)
        return MyViewHolder(view)  //tworzy
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val success = currencyList[position].success

        if(!success){
            val error = currencyList[position].error.type
            if(error=="no_rates_available")     holder.Currency.text="Brak dalszych danych do wyświetlenia"
        }
        else{
            val dateOnly = SimpleDateFormat("yyyy-MM-dd")
            val date = dateOnly.format(currencyList[position].date)
            val aud = currencyList[position].rates.aud
            val cad = currencyList[position].rates.cad
            val pln = currencyList[position].rates.pln
            val usd = currencyList[position].rates.usd
            val show = "aud: $aud cad: $cad pln: $pln usd: $usd"
            holder.dateTv.text = date
            holder.Currency.text = show
        }

    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var Currency: TextView
        internal var dateTv: TextView


        init {
            dateTv = itemView.findViewById(R.id.dateRecyclerId)
            Currency = itemView.findViewById(R.id.currenciesRecyclerId)

        }
    }

     fun  addItems(list: MutableList<Currencies>){
         for(i in list)
         for (currency: Currencies in list) {
             currencyList.add(currency)
         }
         notifyDataSetChanged()
    }
}