package pt.isel.jht.pdm.hangman

import android.os.Bundle
import androidx.lifecycle.ViewModel

class HangmanViewModel : ViewModel() {

    private var gameState = HangmanGame.startGame()

    val visibleWord
        get() = gameState.word.map { c -> if (gameState.right.contains(c)) c else '_' }.joinToString("")

    val wrongLetters
        get() = gameState.wrong.joinToString("").padEnd(HangmanGame.maxWrong, '_')

    val gameResultMessage
        get() = when (HangmanGame.gameResult(gameState)) {
            HangmanGameResult.SUCCEEDED -> "Congratulations! :-)"
            HangmanGameResult.FAILED -> "HANGED! :-("
            HangmanGameResult.ONGOING -> null
        }

    fun guessWith(guess: String): String? {
        return when (guess.length) {
            0 -> "Missing letter"
            1 -> guessWith(guess[0])
            else -> "Too many characters"
        }
    }

    private fun guessWith(guess: Char): String? {
        try {
            gameState = HangmanGame.guess(guess, gameState)
        } catch (err: HangmanGuessException) {
            return when (err.type) {
                HangmanGuessValidation.ALREADY_USED -> "Letter already used"
                HangmanGuessValidation.NOT_A_LETTER -> "Invalid character"
            }
        }
        return null
    }

    private val KEY_WORD  = "KEY_WORD"
    private val KEY_RIGHT = "KEY_RIGHT"
    private val KEY_WRONG = "KEY_WRONG"

    fun saveState(outState: Bundle) {
        outState.putString(KEY_WORD, gameState.word)
        outState.putCharArray(KEY_RIGHT, gameState.right.toCharArray())
        outState.putCharArray(KEY_WRONG, gameState.wrong.toCharArray())
    }

    fun loadState(savedState: Bundle) {
        val word = savedState.getString(KEY_WORD)
        if (word != null) {
            gameState = HangmanGameState(
                word,
                savedState.getCharArray(KEY_RIGHT)?.toSet() ?: setOf(),
                savedState.getCharArray(KEY_WRONG)?.toSet() ?: setOf()
            )
        }
    }
}
