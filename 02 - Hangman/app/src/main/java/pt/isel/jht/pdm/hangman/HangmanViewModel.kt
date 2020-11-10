package pt.isel.jht.pdm.hangman

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import java.util.Date

class HangmanViewModel(private val app: Application, private val state : SavedStateHandle) : AndroidViewModel(app) {

    private val GAME_STATE_KEY = "GAME_STATE_KEY"
    private val GAME_SAVED_KEY = "GAME_SAVED_KEY"
    private val EXTRA_LINE_KEY = "EXTRA_LINE_KEY"

    init {
        if (state.contains(GAME_STATE_KEY)) {
            Log.d("Hangman::ViewModel", "Restoring saved state")
        } else {
            Log.d("Hangman::ViewModel", "Creating a new view model")
        }
    }

    private val repository = app.repository

    private var gameState = state.get<HangmanGameState>(GAME_STATE_KEY) ?: HangmanGame.startGame()
        set(gs) {
            field = gs
            state[GAME_STATE_KEY] = gs
        }

    private var saved = state.get<Boolean>(GAME_SAVED_KEY) ?: false
        set(sv) {
            field = sv
            state[GAME_SAVED_KEY] = sv
        }

    var line = state.get<Line>(EXTRA_LINE_KEY)
        set(ln) {
            field = ln
            state[EXTRA_LINE_KEY] = ln
        }

    val visibleWord
        get() = gameState.word.map { c -> if (gameState.right.contains(c)) c else '_' }.joinToString("")

    val wrongLetters
        get() = gameState.wrong.joinToString("").padEnd(HangmanGame.maxWrong, '_')

    val numErrors
        get() = gameState.wrong.size

    val gameResultMessage
        get() = when (HangmanGame.gameResult(gameState)) {
            HangmanGameResult.SUCCEEDED -> app.getString(R.string.congrats)
            HangmanGameResult.FAILED -> app.getString(R.string.hanged)
            HangmanGameResult.ONGOING -> null
        }

    val history
        get() = repository.allHistoryItems

    fun guessWith(guess: String): String? {
        return when (guess.length) {
            0 -> app.getString(R.string.missing_letter)
            1 -> guessWith(guess[0])
            else -> app.getString(R.string.too_many_chars)
        }
    }

    private fun guessWith(guess: Char): String? {
        try {
            gameState = HangmanGame.guess(guess, gameState)
        } catch (err: HangmanGuessException) {
            return when (err.type) {
                HangmanGuessValidation.ALREADY_USED -> app.getString(R.string.letter_already_used)
                HangmanGuessValidation.NOT_A_LETTER -> app.getString(R.string.invalid_character)
            }
        }
        return null
    }

    fun saveGame() {
        if (!saved && HangmanGame.gameResult(gameState) != HangmanGameResult.ONGOING) {
            repository.insertHistoryItem(
                HangmanHistoryItem(
                    gameState.word,
                    gameState.right.joinToString(""),
                    gameState.wrong.joinToString(""),
                    Date()
                )
            )
            saved = true
        }
    }
}
