package com.lukasz.cointracker

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.ceil
import kotlin.math.log10
import com.bumptech.glide.request.target.Target
import com.lukasz.cointracker.domain.Coin

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, coins: List<Coin>?) {
    val adapter = recyclerView.adapter as CoinAdapter
    adapter.submitList(coins)
}

@BindingAdapter("price")
fun convertPrice(textView: TextView, price: Double) {
    val formatter =
        DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    formatter.minimumFractionDigits = 2
    val significantDigit = ceil(-log10(price)).toInt()
    formatter.maximumFractionDigits = if (2 > significantDigit) 2 else significantDigit + 1
    val text = "$" + formatter.format(price)
    textView.text = text
}

@BindingAdapter("priceInt")
fun convertPriceInt(textView: TextView, price: Double) {
    val formatter =
        DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    formatter.minimumFractionDigits = 0
    val significantDigit = ceil(-log10(price)).toInt()
    formatter.maximumFractionDigits = if (2 > significantDigit) 2 else significantDigit + 1
    val text = "$" + formatter.format(price)
    textView.text = text
}

@BindingAdapter("date")
fun convertDate(textView: TextView, dateString: String?) {
    val dateArray = dateString?.split("-")

    if (dateArray != null) {
        val dateFormatted = "${dateArray[2].take(2)}.${dateArray[1]}.${dateArray[0]}"
        textView.text = dateFormatted
    } else{
        textView.text = "-"
    }
}


@BindingAdapter("percentage_change")
fun convertPercentageChange(textView: TextView, percentage_change: Double) {
    val formatter =
        DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    val text = formatter.format(percentage_change) + "%"
    textView.text = text
}

@BindingAdapter("billionsDollars")
fun convertBigValuesInDollars(textView: TextView, value: Double) {
    val formatter =
        DecimalFormat("###,###,###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    var text = "$" + formatter.format(value)
    if (text.compareTo("$0") == 0) {
        text = "-"
    }
    textView.text = text
}

@BindingAdapter("billions")
fun convertBigValues(textView: TextView, value: Double) {
    val formatter =
        DecimalFormat("###,###,###,###", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
    var text = formatter.format(value)
    if (text.compareTo("0") == 0) {
        text = "-"
    }
    textView.text = text
}

@ExperimentalStdlibApi
@BindingAdapter("coinName")
fun convertCoinName(textView: TextView, name: String) {
    var text = name.replace("-", " ")
    val array = text.split(" ").map { it.capitalize(Locale.ROOT) }
    text = array.joinToString(" ")
    textView.text = text
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imgView.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imgView.visibility = View.VISIBLE
                    return false
                }
            })
            .into(imgView)
    }
}
