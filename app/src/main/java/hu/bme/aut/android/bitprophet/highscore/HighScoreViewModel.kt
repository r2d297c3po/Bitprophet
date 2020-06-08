package hu.bme.aut.android.bitprophet.highscore

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import hu.bme.aut.android.bitprophet.database.PriceDatabaseDao

class HighScoreViewModel(
    val database: PriceDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    val tips = database.getAllTips()
    val score = database.getLiveScore()
    val highScore = database.getHighScore()
    init {
        Log.d("HighScoreTip","score.value = ${score.value}")
    }
}
