package com.example.qpony

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View.*
import android.widget.*
import com.example.ad.retrofittest.Common_Clases.ErrorHandler.CallbackWrapper
import com.example.qpony.Network.APIService
import com.example.qpony.Network.ApiUtils
import com.example.qpony.Model.Currencies
import com.example.qpony.RecyclerViews.RecyclerViewMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import java.util.*


class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var layoutManager: LinearLayoutManager? = null
    private var recyclerAdapter: RecyclerViewMain? = null


    private var apiServiceProfile: APIService? = null
    private val compositeDisposable = CompositeDisposable()
    private var dateOnly="2007-02-03"
    private var currencyList: MutableList<Currencies> = ArrayList()

    private var isLoading = true
    private var pastVisibleItems = 0
    private var  visibleItemCount = 0
    private var totalItemCount = 0
    private var previousTotal = 0
    private var viewTreshold = 0

    private val key = "6434613810975d4a8ed1cefc36a09461"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiServiceProfile = ApiUtils.getAPIService(applicationContext)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)



        layoutManager = LinearLayoutManager(baseContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.i("asdasd", "1")

                visibleItemCount = layoutManager!!.childCount
                totalItemCount = layoutManager!!.itemCount
                pastVisibleItems = layoutManager!!.findFirstCompletelyVisibleItemPosition()

                if(dy>0){ // did user scrolled?
                    Log.i("asdasd", "2")
                    if(isLoading){
                        Log.i("asdasd", "3")
                        if(totalItemCount>previousTotal) { //it has loaded
                            Log.i("asdasd", "4")
                            isLoading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if(!isLoading && (totalItemCount-visibleItemCount)<=(pastVisibleItems+viewTreshold)){
                        Log.i("asdasd", "5")
                        pagination()
                        isLoading = true
                    }

                }
            }
        })

        for(i in 1..7){
            getCurrencies()
        }

    }

    fun getCurrencies() {
        dateOnly =  provideUrl(dateOnly)
        val url = "http://data.fixer.io/api/$dateOnly?access_key=$key&symbols=USD,AUD,CAD,PLN,MXN&format=1"
        compositeDisposable.add(apiServiceProfile
                !!.IgetCurrencies(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<Currencies>(baseContext) {
                    override fun onNext(response: Currencies) {
                        currencyList.add(response)

                        recyclerAdapter = RecyclerViewMain(currencyList, baseContext)
                        recyclerView!!.adapter = recyclerAdapter
                        progressBar!!.visibility = GONE

                    }}))
    }

    fun pagination(){
        Log.i("asdasd", "6")
        progressBar!!.visibility = VISIBLE
        dateOnly =  provideUrl(dateOnly)
        val url = "http://data.fixer.io/api/$dateOnly?access_key=$key&symbols=USD,AUD,CAD,PLN,MXN&format=1"
        compositeDisposable.add(apiServiceProfile
        !!.IgetCurrencies(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<Currencies>(baseContext) {
                    override fun onNext(response: Currencies) {
                        Log.i("asdasd", "7")
                        val success = response.success

                        if(success){
                            Log.i("asdasd", "8")
                            val list: MutableList<Currencies> = ArrayList()
                            list.add(response)
                            recyclerAdapter!!.addItems(list)

                        }
                        else{
                            Log.i("asdasd", "9")
                            val error = response.error.type
                            if(error=="no_rates_available")  Toast.makeText(baseContext, "Brak danych", Toast.LENGTH_SHORT).show()
                            else if(error == "usage_limit_reached")
                                Toast.makeText(baseContext, "Osiągnięto limit zapytań dla konta darmowego", Toast.LENGTH_SHORT).show()
                            else   Toast.makeText(baseContext, "Coś poszło nie tak", Toast.LENGTH_SHORT).show()

                        }

                        progressBar!!.visibility = GONE

                    }}))


    }

    fun provideUrl(date: String): String{
        Log.i("asdProvide", date)
        val year = date.substring(0,4)
        val month = date.substring(5,7)
        val day = date.substring(8,10)

        var newDay = day.toInt()
        var newMonth = month.toInt()
        var newYear = year.toInt()

        newDay--

        if(newDay==0){
            newMonth--
            when(newMonth.toString()){
                "1" -> newDay = 31
                "2" -> {
                    val stringYear = newYear.toString()
                    if(stringYear=="2016" || stringYear=="2012" || stringYear=="2008" ||
                            stringYear=="2002"|| stringYear=="1998"|| stringYear=="1994" ||
                            stringYear=="1990" || stringYear=="1986") newDay=29
                    else newDay = 28
                }
                "3" -> newDay = 31
                "4" -> newDay = 30
                "5" -> newDay = 31
                "6" -> newDay = 30
                "7" -> newDay = 31
                "8" -> newDay = 31
                "9" -> newDay = 30
                "10" -> newDay = 31
                "11" -> newDay = 30
                "12" -> newDay = 31
            }
            Log.i("asd", newDay.toString())
        }

        if(newMonth==0){
            newYear--
            newMonth=12
            newDay=31
        }

        var newDayString = newDay.toString()
        var newMonthString = newMonth.toString()
        val newYearString = newYear.toString()

        Log.i("asdString", "$newDayString $newMonthString $newYearString")
        if(newDayString.length==1) newDayString = "0$newDayString"
        if(newMonthString.length==1) newMonthString = "0$newMonthString"


       val result = "$newYearString-$newMonthString-$newDayString"
        Log.i("asdResult2", result)
        return result
    }


    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

}
