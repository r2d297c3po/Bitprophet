package hu.bme.aut.android.bitprophet.work

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import hu.bme.aut.android.bitprophet.database.PriceDatabase.Companion.getInstance
import hu.bme.aut.android.bitprophet.notification.sendNotification
import hu.bme.aut.android.bitprophet.repository.PriceRepository

class PriceWork(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "PriceWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = PriceRepository(database)

        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        return try {
            repository.newRound()
            notificationManager.sendNotification(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Log.d("PriceWork", "${e.message}")
            Result.retry()
        }
    }


}