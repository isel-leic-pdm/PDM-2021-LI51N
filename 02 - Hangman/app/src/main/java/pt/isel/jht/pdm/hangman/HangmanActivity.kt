package pt.isel.jht.pdm.hangman

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class HangmanActivity : AppCompatActivity() {

    private val txtWord   by lazy { findViewById<TextView>(R.id.txtWord) }
    private val txtWrong  by lazy { findViewById<TextView>(R.id.txtWrong) }
    private val edtLetter by lazy { findViewById<EditText>(R.id.edtLetter) }
    private val butGuess  by lazy { findViewById<Button>(R.id.butGuess) }
    private val txtResult  by lazy { findViewById<TextView>(R.id.txtResult) }

    private val viewModel by lazy { ViewModelProvider(this).get(HangmanViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            Log.d("Hangman::loadState", "Loading state for ${hashCode()}")
            viewModel.loadState(savedInstanceState)
        }

        updateText()

        butGuess.setOnClickListener { onGuess() }
    }

    private fun updateText() {
        txtWord.text = viewModel.visibleWord.spaced()
        txtWrong.text = viewModel.wrongLetters.spaced()

        val gameResultMessage = viewModel.gameResultMessage
        if (gameResultMessage != null) {
            txtResult.text = gameResultMessage
            edtLetter.isEnabled = false
            butGuess.isEnabled = false
        }
    }

    private fun onGuess() {
        val guess = edtLetter.text.toString()
        when (val errorMsg = viewModel.guessWith(guess)) {
            null -> updateText()
            else -> Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        }
        edtLetter.text.clear()
    }

    private fun String.spaced() = this.toCharArray().joinToString(" ")

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!isChangingConfigurations) {
            Log.d("Hangman::saveState", "Saving state for ${hashCode()}")
            viewModel.saveState(outState)
        }
    }
}
