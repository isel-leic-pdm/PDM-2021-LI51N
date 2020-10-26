package pt.isel.jht.pdm.hangman

object HangmanGame {

    const val maxWrong = 7

    fun getRandomWord() = Words.getRandomWord()

    fun guess(word: String, guess: Char, right: Set<Char>, wrong: Set<Char>) =
        if (word.contains(guess))
            Pair(right + guess, wrong)
        else
            Pair(right, wrong + guess)

}
