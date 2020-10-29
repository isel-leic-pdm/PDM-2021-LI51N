package pt.isel.jht.pdm.hangman

data class HangmanGameState(
    val word: String,
    val right: Set<Char> = setOf(),
    val wrong: Set<Char> = setOf()
)
