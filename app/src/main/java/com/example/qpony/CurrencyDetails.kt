package com.example.qpony

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_currency_details.*

class CurrencyDetails : AppCompatActivity() {

    private var dateTv: TextView? = null
    private var nameTv: TextView? = null
    private var valueTv: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_details)

        dateTv = dateDetailsTv
        nameTv = nameDetailsTv
        valueTv = valueDetailsTv

        val date = intent.getStringExtra("date")
        val name = intent.getStringExtra("name")
        val value = intent.getStringExtra("value")

        dateTv!!.text = date
        nameTv!!.text = name
        valueTv!!.text = value
    }
}
