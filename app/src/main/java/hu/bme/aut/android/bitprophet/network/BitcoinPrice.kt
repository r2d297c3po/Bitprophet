package hu.bme.aut.android.bitprophet.network

data class BitcoinPrice(
    val timestamp: Double,
    val timestampms: Double,
    val tid: Double,
    val price: String,
    val amount: String,
    val exchange: String,
    val type: String
)