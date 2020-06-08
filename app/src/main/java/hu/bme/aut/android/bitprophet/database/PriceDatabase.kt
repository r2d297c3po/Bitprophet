package hu.bme.aut.android.bitprophet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Initialize the database according to the pattern that google recommends

@Database(entities = [PriceTip::class, Score::class], version = 1, exportSchema = false)
abstract class PriceDatabase : RoomDatabase() {

    abstract val priceDatabaseDao: PriceDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: PriceDatabase? = null

        fun getInstance(context: Context): PriceDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PriceDatabase::class.java,
                        "price_tips_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance


                }
                return instance
            }
        }
    }
}