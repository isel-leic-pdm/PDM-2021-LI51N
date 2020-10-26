package pt.isel.jht.pdm.hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class HangmanActivity : AppCompatActivity() {

    private val txtWord   by lazy { findViewById<TextView>(R.id.txtWord) }
    private val txtWrong  by lazy { findViewById<TextView>(R.id.txtWrong) }
    private val edtLetter by lazy { findViewById<EditText>(R.id.edtLetter) }
    private val butGuess  by lazy { findViewById<Button>(R.id.butGuess) }

    private val viewModel by lazy { ViewModelProvider(this).get(HangmanViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateText()

        butGuess.setOnClickListener { onGuess() }
    }

    private fun updateText() {
        txtWord.text = viewModel.getVisibleWord()
        txtWrong.text = viewModel.getWrongLetters()
    }

    private fun onGuess() {
        val guess = edtLetter.text.toString()
        val errorMsg = viewModel.guessWith(guess)
        if (errorMsg != null) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        } else {
            updateText()
        }
        edtLetter.text.clear()
    }
}