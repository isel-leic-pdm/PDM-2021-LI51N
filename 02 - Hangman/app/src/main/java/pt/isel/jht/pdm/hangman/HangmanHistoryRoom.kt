package pt.isel.jht.pdm.hangman

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date

@Entity(tableName = "history")
data class HangmanHistoryItem(var word: String, var right: String, var wrong: String,vardate:Date) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}

@Dao
interface HangmanHistoryDao {

    @Insert
    fun insertItem(item: HangmanHistoryItem)

    @Query("DELETE FROM history")
    fun deleteAllItems()

    @Query("SELECT * FROM history ORDER BY date DESC")
    fun getAllItems() : LiveData<List<HangmanHistoryItem>>
}

@Database(entities = [HangmanHistoryItem::class], version = 2)
@TypeConverters(Converters::class)
abstract class HangmanHistoryDatabase : RoomDatabase() {
    abstract fun historyDao() : HangmanHistoryDao
}

class Converters {
    @TypeConverter
    fun toDate(timestamp: Long) = Date(timestamp)

    @TypeConverter
    fun fromDate(date: Date) = date.time
}
