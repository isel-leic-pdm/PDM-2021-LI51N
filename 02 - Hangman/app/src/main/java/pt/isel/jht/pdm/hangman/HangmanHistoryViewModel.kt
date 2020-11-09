package pt.isel.jht.pdm.hangman

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class HangmanHistoryViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = app.repository

    val history
        get() = repository.allHistoryItems

}
