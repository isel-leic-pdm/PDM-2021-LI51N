package pt.isel.jht.pdm.hangman

import java.io.SyncFailedException
import java.util.Locale
import kotlin.math.max

object HangmanGame {

    const val maxWrong = 7

    fun startGame() = HangmanGameState(Words.getRandomWord().toUpperCase(Locale.getDefault()))

    fun gameResult(state: HangmanGameState) =
        when {
            state.wrong.size == maxWrong -> HangmanGameResult.FAILED
            state.word.all { state.right.contains(it) } -> HangmanGameResult.SUCCEEDED
            else -> HangmanGameResult.ONGOING
        }

    fun guess(guess: Char, state: HangmanGameState) =
        if (state.word.contains(validate(guess.toUpperCase(), state)))
            HangmanGameState(state.word, state.right + guess, state.wrong)
        else
            HangmanGameState(state.word, state.right, state.wrong + guess)

    private fun validate(guess: Char, state: HangmanGameState) =
        when (guess) {
            !in 'A'..'Z' -> throw HangmanGuessException(HangmanGuessValidation.NOT_A_LETTER)
            in state.right, in state.wrong -> throw HangmanGuessException(HangmanGuessValidation.REPEATED)
            else -> guess
        }
}

class HangmanGuessException(val type: HangmanGuessValidation) : Exception()

enum class HangmanGuessValidation {
    REPEATED, NOT_A_LETTER
}

enum class HangmanGameResult {
    FAILED, ONGOING, SUCCEEDED
}
