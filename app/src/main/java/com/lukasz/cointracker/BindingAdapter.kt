package com.lukasz.cointracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.d
import com.lukasz.cointracker.network.Data
import java.lang.Math.pow
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.ceil
import kotlin.math.log10


@BindingAdapter ("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Data>?) {
    val adapter =recyclerView.adapter as CoinAdapter
    adapter.submitList(data)
}

@BindingAdapter("price")
fun convertPrice(textView: TextView, price: Double){
    val formatter =
        DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    formatter.minimumFractionDigits= 2
    val significantDigit = ceil(-log10(price)).toInt()
    formatter.maximumFractionDigits= if(2 > significantDigit) 2 else significantDigit +1
    val text =  "$" + formatter.format(price)
    textView.text = text
}

@BindingAdapter("percentage_change")
fun convertPercentageChange(textView: TextView, percentage_change: Double){
    val formatter =
        DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    val text =  formatter.format(percentage_change) + "%"
    textView.text = text
}

@BindingAdapter("billions")
fun convertBigValues(textView: TextView, value: Double){
    val formatter =
            DecimalFormat("###,###,###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    val text =  "$" + formatter.format(value)
    textView.text = text
}

@ExperimentalStdlibApi
@BindingAdapter("coinName")
fun convertCoinName(textView: TextView, name: String){
    var text =  name.replace("-", " ")
    val array = text.split(" ").map { it.capitalize(Locale.ROOT) }
    text = array.joinToString(" ")
    textView.text = text
}