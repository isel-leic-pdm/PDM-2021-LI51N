package pt.isel.jht.pdm.hangman

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "history")
data class HangmanHistoryItem(var word: String, var right: String, var wrong: String) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}

@Dao
interface HangmanHistoryDao {

    @Insert
    fun insertItem(item: HangmanHistoryItem)

    @Query("DELETE FROM history")
    fun deleteAllItems()

    @Query("SELECT * FROM history ORDER BY word ASC")
    fun getAllItems() : LiveData<List<HangmanHistoryItem>>
}

@Database(entities = [HangmanHistoryItem::class], version = 1)
abstract class HangmanHistoryDatabase : RoomDatabase() {
    abstract fun historyDao() : HangmanHistoryDao
}
