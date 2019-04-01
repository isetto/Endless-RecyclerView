package com.example.qpony.RecyclerViews

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.qpony.Model.Currencies
import com.example.qpony.Model.RateModel
import com.example.qpony.R
import kotlinx.android.synthetic.main.activity_recycler_view_main.view.*
import java.text.SimpleDateFormat




class RecyclerViewMain(private val currencyList: MutableList<Currencies>, private val context: Context?) : RecyclerView.Adapter<RecyclerViewMain.MyViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  //parent - kontener z informacjami. Layoutinflater laczy kontener(parent)
            : MyViewHolder
    // z userslist
    { // view podstawowa klasa po ktorej dziedzicza kontrolki
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_view_main, parent, false)
        return MyViewHolder(view)  //tworzy
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


            val dateOnly = SimpleDateFormat("yyyy-MM-dd")
            val date = dateOnly.format(currencyList[position].date)
            holder.dateTv.text = date
            val rate = currencyList[position].rates
            var ratesList: List<RateModel> = listOf(RateModel("AUD", rate!!.aud), RateModel("CAD", rate.cad),
                    RateModel("PLN", rate.pln), RateModel("MXN", rate.mxn), RateModel("USD", rate.usd))

            val childLayoutManager = LinearLayoutManager(holder.recyclerViewChild.context, LinearLayout.HORIZONTAL, false)
            childLayoutManager.initialPrefetchItemCount = 4



            holder.recyclerViewChild.apply {
                layoutManager = childLayoutManager
                adapter = RecyclerViewChild(ratesList, date)
                setRecycledViewPool(viewPool)
            }

    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       // internal var Currency: TextView
        internal var dateTv: TextView = itemView.dateRecyclerId
        internal val recyclerViewChild : RecyclerView = itemView.recyclerChildId

    }

     fun  addItems(list: MutableList<Currencies>){
         for(i in list)
         for (currency: Currencies in list) {
             currencyList.add(currency)
         }
         notifyDataSetChanged()
    }
}