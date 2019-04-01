package com.example.qpony

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.view.View.*
import android.widget.*
import com.example.qpony.ErrorHandler.CallbackWrapper
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
    private var dateOnly="2015-05-22"
    private var currencyList: MutableList<Currencies> = ArrayList()

    private var isLoading = true
    private var pastVisibleItems = 0
    private var  visibleItemCount = 0
    private var totalItemCount = 0
    private var previousTotal = 0
    private var viewTreshold = 0
    private var isDone = false
    private var counter = 0


    private val apiKey = "8ea9f7a4a4bd92a448815976168a9ea1"
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


                visibleItemCount = layoutManager!!.childCount
                totalItemCount = layoutManager!!.itemCount
                pastVisibleItems = layoutManager!!.findFirstCompletelyVisibleItemPosition()

                if(dy>0){ // did user scrolled?
                    if(isLoading){
                        if(totalItemCount>previousTotal) { //it has loaded
                            isLoading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if(!isLoading && (totalItemCount-visibleItemCount)<=(pastVisibleItems+viewTreshold)){
                        pagination()
                        isLoading = true
                    }

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getCurrencies()

    }

    fun await (){
        while(isDone && counter<5){
            counter++
            isDone = false
            getCurrencies()
        }
    }

    fun getCurrencies() {
        dateOnly =  provideUrl(dateOnly)
        val url = "http://data.fixer.io/api/$dateOnly?access_key=$apiKey&symbols=USD,AUD,CAD,PLN,MXN&format=1"
        compositeDisposable.add(apiServiceProfile
                !!.IgetCurrencies(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<Currencies>(baseContext) {
                    override fun onNext(response: Currencies) {

                        val success = response.success
                        if(success!!){
                            currencyList.add(response)

                            recyclerAdapter = RecyclerViewMain(currencyList, baseContext)
                            recyclerView!!.adapter = recyclerAdapter

                        }
                        else{
                            val error = response.error!!.type
                            if(error=="no_rates_available")  Toast.makeText(baseContext, "Brak danych", Toast.LENGTH_SHORT).show()
                            else if(error == "usage_limit_reached")
                                Toast.makeText(baseContext, "Osiągnięto limit zapytań dla konta darmowego", Toast.LENGTH_SHORT).show()
                            else   Toast.makeText(baseContext, "Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onComplete() {
                        super.onComplete()
                        isDone=true
                        await ()
                    }
                }))
    }

    fun pagination(){
        progressBar!!.visibility = VISIBLE
        dateOnly =  provideUrl(dateOnly)
        val url = "http://data.fixer.io/api/$dateOnly?access_key=$apiKey&symbols=USD,AUD,CAD,PLN,MXN&format=1"
        compositeDisposable.add(apiServiceProfile
        !!.IgetCurrencies(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<Currencies>(baseContext) {
                    override fun onNext(response: Currencies) {
                        val success = response.success

                        if(success!!){
                            val list: MutableList<Currencies> = ArrayList()
                            list.add(response)
                            recyclerAdapter!!.addItems(list)

                        }
                        else{
                            val error = response.error!!.type
                            if(error=="no_rates_available")  Toast.makeText(baseContext, "Brak danych", Toast.LENGTH_SHORT).show()
                            else if(error == "usage_limit_reached")
                                Toast.makeText(baseContext, "Osiągnięto limit zapytań dla konta darmowego", Toast.LENGTH_SHORT).show()
                            else   Toast.makeText(baseContext, "Coś poszło nie tak", Toast.LENGTH_SHORT).show()

                        }

                        progressBar!!.visibility = GONE

                    }}))


    }

    fun provideUrl(date: String): String{
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
                            stringYear=="2002"|| stringYear=="1998") newDay=29
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
        }

        if(newMonth==0){
            newYear--
            newMonth=12
            newDay=31
        }

        var newDayString = newDay.toString()
        var newMonthString = newMonth.toString()
        val newYearString = newYear.toString()

        if(newDayString.length==1) newDayString = "0$newDayString"
        if(newMonthString.length==1) newMonthString = "0$newMonthString"


       val result = "$newYearString-$newMonthString-$newDayString"
        return result
    }


    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

}
