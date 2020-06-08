package hu.bme.aut.android.bitprophet.tip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import hu.bme.aut.android.bitprophet.database.PriceDatabase
import hu.bme.aut.android.bitprophet.database.PriceTip
import hu.bme.aut.android.bitprophet.database.Score
import hu.bme.aut.android.bitprophet.repository.PriceRepository
import hu.bme.aut.android.bitprophet.work.PriceWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TipViewModel(
    val database: PriceDatabase,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val repository = PriceRepository(database)

    val recentTip = database.priceDatabaseDao.getMostRecentTipLive()
    val score = database.priceDatabaseDao.getLiveScore()

    fun onSubmit(tip: String) {
        val priceTip = PriceTip(tippedPrice = tip.toDouble())
        uiScope.launch {
            repository.insert(priceTip)
            if (repository.getScore() == null) {
                repository.insertScore(Score())
            }
            setupWork()
        }
    }

    private fun setupWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val priceRequest = OneTimeWorkRequestBuilder<PriceWork>()
            .setConstraints(constraints)
                // For testing only!!
            .setInitialDelay(5,TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance().enqueueUniqueWork(
            PriceWork.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            priceRequest
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
