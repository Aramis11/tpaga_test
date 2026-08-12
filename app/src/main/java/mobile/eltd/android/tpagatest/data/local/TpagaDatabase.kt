package mobile.eltd.android.tpagatest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovementEntity::class], version = 1, exportSchema = false)
abstract class TpagaDatabase : RoomDatabase() {

    abstract fun movementDao(): MovementDao
}