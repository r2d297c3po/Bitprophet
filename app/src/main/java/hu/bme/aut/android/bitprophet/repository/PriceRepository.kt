package hu.bme.aut.android.bitprophet.repository

import android.util.Log
import androidx.lifecycle.LiveData
import hu.bme.aut.android.bitprophet.database.PriceDatabase
import hu.bme.aut.android.bitprophet.database.PriceTip
import hu.bme.aut.android.bitprophet.database.Score
import hu.bme.aut.android.bitprophet.getTipPoint
import hu.bme.aut.android.bitprophet.network.BitcoinPrice
import hu.bme.aut.android.bitprophet.network.PriceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PriceRepository(private val database: PriceDatabase) {

    suspend fun newRound() {
        withContext(Dispatchers.IO) {
            lateinit var score: Score
            lateinit var priceTip: PriceTip
            database.priceDatabaseDao.apply {
                score = getScore()!!
                priceTip = getTip()!!

                priceTip.realPrice = getPrice(priceTip)!!.price.toDouble()
                priceTip.tipPoint = priceTip.getTipPoint()

                score.point -= priceTip.tipPoint
                if (score.point <= 0) {
                    insertScore(Score())
                } else {
                    score.round += 1
                }

                update(priceTip)
                updateScore(score)
            }
        }
    }

    private suspend fun getPrice(priceTip: PriceTip): BitcoinPrice? {
        return withContext(Dispatchers.IO) {
            val time = (priceTip.tipEndTime / 1000 - 100).toString()
            try {
                val prices = PriceApi.retrofitService.getPrice(time).await()
                prices[0]
            } catch (e: Exception) {
                Log.d("PriceRepository", "getPrice: ${e.message}")
                null
            }
        }
    }

    suspend fun insert(priceTip: PriceTip) {
        withContext(Dispatchers.IO) {
            database.priceDatabaseDao.insert(priceTip)
        }
    }

    suspend fun getTip() : PriceTip? {
        return withContext(Dispatchers.IO) {
            database.priceDatabaseDao.getMostRecentTip()
        }
    }

    suspend fun getTipLive() : LiveData<PriceTip?> {
        return withContext(Dispatchers.IO) {
            database.priceDatabaseDao.getMostRecentTipLive()
        }
    }

    suspend fun insertScore(score: Score) {
        Log.d("PriceRepositoryTip","InsertScore was called")
        withContext(Dispatchers.IO) {
            database.priceDatabaseDao.insertScore(score)
        }
    }

    suspend fun getScore(): Score? {
        return withContext(Dispatchers.IO) {
            database.priceDatabaseDao.getScore()
        }
    }

    suspend fun updateScore(score: Score) {
        withContext(Dispatchers.IO) {
            database.priceDatabaseDao.updateScore(score)
        }
    }
}