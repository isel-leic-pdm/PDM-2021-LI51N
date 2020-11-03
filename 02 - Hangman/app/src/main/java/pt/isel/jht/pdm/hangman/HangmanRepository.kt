package pt.isel.jht.pdm.hangman

import java.util.concurrent.Executors

class HangmanRepository(private val database: HangmanHistoryDatabase) {

    private val executor = Executors.newSingleThreadExecutor()

    private val historyDao = database.historyDao()

    val allHistoryItems
        get() = historyDao.getAllItems()

    fun insertHistoryItem(item: HangmanHistoryItem) {
        executor.submit { historyDao.insertItem(item) }
    }

    fun deleteHistory() {
        executor.submit { historyDao.deleteAllItems() }
    }
}
