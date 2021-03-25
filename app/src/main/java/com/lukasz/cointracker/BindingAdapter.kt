package com.lukasz.cointracker

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.d
import com.lukasz.cointracker.network.Data
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


@BindingAdapter ("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Data>?) {
    val adapter =recyclerView.adapter as CoinAdapter
    adapter.submitList(data)
}

@BindingAdapter("price")
fun convertPrice(textView: TextView, price: Double){
    val formatter =
        DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    val text =  "$" + formatter.format(price)
    textView.text = text
}

@BindingAdapter("percentage_change")
fun convertPercentageChange(textView: TextView, percentage_change: Double){
    val formatter =
        DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    val text =  formatter.format(percentage_change) + "%"
    textView.text = text
}