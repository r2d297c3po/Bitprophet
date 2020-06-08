package hu.bme.aut.android.bitprophet.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.gemini.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface PriceApiService {
    @GET("v1/trades/btcusd")
    fun getPrice(
        @Query("since") timestamp: String,
        @Query("limit_trades") limitTrades: String = "1"
    ):
            Deferred<List<BitcoinPrice>>
}

object PriceApi {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : PriceApiService by lazy {
        retrofit.create(PriceApiService::class.java)
    }
}