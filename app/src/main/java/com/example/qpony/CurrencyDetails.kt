package com.example.qpony

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_currency_details.*

class CurrencyDetails : AppCompatActivity() {

    private var detailsTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_details)

        detailsTV = detailsTv
        val details = intent.getStringExtra("currency")
        detailsTV!!.text = details
    }
}
