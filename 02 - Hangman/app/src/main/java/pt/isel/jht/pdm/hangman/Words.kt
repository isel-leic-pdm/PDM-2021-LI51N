package pt.isel.jht.pdm.hangman

import kotlin.random.Random

object Words {

    private val words = arrayOf(
        "carro", "piano", "dedal", "viola",
        "boneca", "macaco", "cebola", "azeite",
        "cozinha", "rebolar", "andaime", "virtude",
        "impresso", "almofada", "alcatifa", "escritor"
    )

    private val rand = Random(System.nanoTime())

    fun getRandomWord() = words[rand.nextInt(words.size)]
}