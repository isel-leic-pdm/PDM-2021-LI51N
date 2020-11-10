package pt.isel.jht.pdm.hangman

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HangmanGameState(
    val word: String,
    val right: Set<Char> = setOf(),
    val wrong: Set<Char> = setOf()
) : Parcelable
