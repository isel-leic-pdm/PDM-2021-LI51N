package pt.isel.jht.pdm.hangman

import android.app.Application
import androidx.room.Room

class HangmanApp : Application() {

    private val database by lazy {
        Room.databaseBuilder(this, HangmanHistoryDatabase::class.java, "history_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val repository by lazy {
        HangmanRepository(database)
    }
}

val Application.repository
    get() = (this as HangmanApp).repository
