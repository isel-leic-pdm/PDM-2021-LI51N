package pt.isel.jht.pdm.hangman

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import java.util.Date

class HangmanViewModel(private val app: Application) : AndroidViewModel(app) {

    private val repository = app.repository

    private var gameState = HangmanGame.startGame()

    private var saved = false

    var line: Line? = null

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

    private val KEY_SAVED = "KEY_SAVED"
    private val KEY_WORD  = "KEY_WORD"
    private val KEY_RIGHT = "KEY_RIGHT"
    private val KEY_WRONG = "KEY_WRONG"
    private val KEY_LINE  = "KEY_LINE"

    fun saveState(outState: Bundle) {
        outState.putBoolean(KEY_SAVED, saved)
        outState.putString(KEY_WORD, gameState.word)
        outState.putCharArray(KEY_RIGHT, gameState.right.toCharArray())
        outState.putCharArray(KEY_WRONG, gameState.wrong.toCharArray())
        line?.let { ln ->
            outState.putFloatArray(KEY_LINE,
                floatArrayOf(ln.begin.x, ln.begin.y, ln.end.x, ln.end.y)
            )
        }
    }

    fun loadState(savedState: Bundle) {
        savedState.getString(KEY_WORD)?.let { word ->
            gameState = HangmanGameState(
                word,
                savedState.getCharArray(KEY_RIGHT)?.toSet() ?: setOf(),
                savedState.getCharArray(KEY_WRONG)?.toSet() ?: setOf()
            )
            saved = savedState.getBoolean(KEY_SAVED)
        }
        savedState.getFloatArray(KEY_LINE)?.let { coords ->
            line = Line(Point(coords[0], coords[1]), Point(coords[2], coords[3]))
        }
    }
}
