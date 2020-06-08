package hu.bme.aut.android.bitprophet.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.bitprophet.toNextDay

@Entity(tableName = "price_tips_table")
data class PriceTip (
    @PrimaryKey(autoGenerate = true)
    var tipId: Long = 0L,

    var tipStartTime: Long = System.currentTimeMillis(),

    var tipEndTime: Long = tipStartTime.toNextDay(),

    var tippedPrice: Double = 0.0,

    var realPrice: Double? = null,

    var tipPoint: Double = 0.0
)

@Entity(tableName = "score_table")
data class Score (
    @PrimaryKey(autoGenerate = true)
    var scoreId: Long = 0L,

    var point: Double = 1000.0,

    var round: Int = 0
)