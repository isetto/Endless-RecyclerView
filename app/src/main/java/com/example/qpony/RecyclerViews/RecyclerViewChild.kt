package com.example.qpony.RecyclerViews

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.qpony.CurrencyDetails
import com.example.qpony.R
import kotlinx.android.synthetic.main.activity_recycler_view_child.view.*


class RecyclerViewChild(private val ratesList: List<String>) : RecyclerView.Adapter<RecyclerViewChild.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MyViewHolder

    {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_view_child, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            Log.i("asdqwd1", "work")
            val example = ratesList[position]
            Log.i("asdqwd2", example)

            holder.currencyTv.text = example

        holder.currencyTv.setOnClickListener { v ->
            val context: Context
            val intent: Intent
            context = v.context
            intent = Intent(context, CurrencyDetails::class.java)
            intent.putExtra("currency", example)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       internal var currencyTv: TextView = itemView.currencyChildTvId


    }

}