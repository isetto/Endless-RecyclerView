package com.example.qpony.RecyclerViews

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.qpony.CurrencyDetails
import com.example.qpony.Model.RateModel
import com.example.qpony.R
import kotlinx.android.synthetic.main.activity_recycler_view_child.view.*


class RecyclerViewChild(private val ratesList: List<RateModel>, private val dateString: String) : RecyclerView.Adapter<RecyclerViewChild.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MyViewHolder

    {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_view_child, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val rate = ratesList[position]

        holder.currencyNameTv.text = rate.rateName
        holder.valueTv.text = rate.value.toString()

        holder.layout.setOnClickListener { v ->
            val context: Context = v.context
            val intent: Intent
            intent = Intent(context, CurrencyDetails::class.java)
            intent.putExtra("date", dateString)
            intent.putExtra("name", rate.rateName)
            intent.putExtra("value", rate.value.toString())
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var currencyNameTv: TextView = itemView.nameCurrencyTvId
        internal var valueTv: TextView = itemView.valueCurrencyTvId
        internal var layout: LinearLayout = itemView.layoutChildId


    }

}