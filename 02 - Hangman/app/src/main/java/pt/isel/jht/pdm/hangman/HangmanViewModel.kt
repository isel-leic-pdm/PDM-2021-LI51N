package pt.isel.jht.pdm.hangman

import androidx.lifecycle.ViewModel

class HangmanViewModel : ViewModel() {

    private val word = HangmanGame.getRandomWord().toUpperCase()

    private var right = setOf<Char>()
    private var wrong = setOf<Char>()

    fun getVisibleWord() : String {
        return word.map { c -> if (right.contains(c)) c else '_' }.joinToString(" ")
    }

    fun getWrongLetters() : String {
        return wrong.joinToString("").padEnd(HangmanGame.maxWrong, '_').toCharArray().joinToString(" ")
    }

    fun guessWith(guess: String): String? {
        return when (guess.length) {
            0 -> "Missing letter"
            1 -> guessWith(guess[0].toUpperCase())
            else -> "Too many characters"
        }
    }

    private fun guessWith(guess: Char): String? {
        return when (guess) {
            !in 'A'..'Z' -> "Invalid character"
            in right, in wrong -> "Letter already used"
            else -> { newGuessWith(guess); null }
        }
    }

    private fun newGuessWith(guess: Char) {
        val (newRight, newWrong) = HangmanGame.guess(word, guess, right, wrong)
        this.right = newRight
        this.wrong = newWrong
    }
}