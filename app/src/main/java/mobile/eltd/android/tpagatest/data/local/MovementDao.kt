package mobile.eltd.android.tpagatest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovementDao {

    @Query("SELECT * FROM movements ORDER BY date DESC")
    suspend fun getAll(): List<MovementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movements: List<MovementEntity>)

    @Query("DELETE FROM movements")
    suspend fun clear()
}