package hu.bme.aut.android.bitprophet.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PriceDatabaseDao {

    @Insert(entity = PriceTip::class)
    fun insert(priceTip: PriceTip)

    @Insert(entity = Score::class)
    fun insertScore(score: Score)

    @Update(entity = PriceTip::class)
    fun update(priceTip: PriceTip)

    @Update(entity = Score::class)
    fun updateScore(score: Score)

    @Query("SELECT * FROM price_tips_table ORDER BY tipId DESC")
    fun getAllTips(): LiveData<List<PriceTip>>

    @Query("SELECT * FROM price_tips_table ORDER BY tipId DESC LIMIT 1")
    fun getMostRecentTip(): PriceTip?

    @Query("SELECT * FROM price_tips_table ORDER BY tipId DESC LIMIT 1")
    fun getMostRecentTipLive(): LiveData<PriceTip?>

    @Query("SELECT * FROM score_table ORDER BY scoreId DESC LIMIT 1")
    fun getLiveScore(): LiveData<Score>

    @Query("SELECT * FROM score_table ORDER BY scoreId DESC LIMIT 1")
    fun getScore(): Score?

    @Query("SELECT * FROM score_table ORDER BY round DESC LIMIT 1")
    fun getHighScore(): LiveData<Score>

    @Query("DELETE FROM price_tips_table ")
    fun deleteAllTips()

    @Query("DELETE FROM score_table ")
    fun deleteAllScores()

//    @Query("SELECT * FROM price_tips_table ORDER BY tipPoint ASC LIMIT 5")
//    fun getTopTips(): LiveData<List<PriceTip>>
}