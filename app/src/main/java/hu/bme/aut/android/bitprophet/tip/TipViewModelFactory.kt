package hu.bme.aut.android.bitprophet.tip

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.bitprophet.database.PriceDatabase

class TipViewModelFactory(
    private val dataSource: PriceDatabase,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TipViewModel::class.java)) {
            return TipViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}