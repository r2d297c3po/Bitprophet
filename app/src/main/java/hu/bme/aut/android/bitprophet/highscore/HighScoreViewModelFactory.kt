package hu.bme.aut.android.bitprophet.highscore

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.bitprophet.database.PriceDatabaseDao

class HighScoreViewModelFactory(
    private val dataSource: PriceDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighScoreViewModel::class.java)) {
            return HighScoreViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}