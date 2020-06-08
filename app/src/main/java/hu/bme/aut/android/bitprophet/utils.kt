package hu.bme.aut.android.bitprophet

import hu.bme.aut.android.bitprophet.database.PriceTip
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.math.abs

private val ONE_DAY_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
private val FIVE_MINUTES_FOR_TESTING = TimeUnit.MILLISECONDS.convert(5,TimeUnit.MINUTES)

fun Long.toNextDay() : Long {
    return (this + FIVE_MINUTES_FOR_TESTING)
}

fun PriceTip.getTipPoint() : Double = abs(this.tippedPrice - this.realPrice!!)

fun Long.toDateHours() : String {
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(this)
}

fun Long.toDate() : String {
    val formatter = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return formatter.format(this)
}

fun Double.round() : String {
    return "%.2f".format(this)
}