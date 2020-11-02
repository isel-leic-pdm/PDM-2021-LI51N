package pt.isel.jht.pdm.hangman

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
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
    private val cvwGallows by lazy { findViewById<GallowsView>(R.id.cvwGallows) }

    private val viewModel by lazy { ViewModelProvider(this).get(HangmanViewModel::class.java) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            Log.d("Hangman::loadState", "Loading state for ${hashCode()}")
            viewModel.loadState(savedInstanceState)
        }

        updateViews()

        butGuess.setOnClickListener { onGuess() }

        cvwGallows.setOnTouchListener { _, event ->
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> { toast("DOWN ($x, $y)") }
                MotionEvent.ACTION_MOVE -> { /* toast("MOVE ($x, $y)") */ }
                MotionEvent.ACTION_UP   -> { toast("UP ($x, $y)") }
            }
            true
        }
    }

    private fun updateViews() {
        txtWord.text = viewModel.visibleWord.spaced()
        txtWrong.text = viewModel.wrongLetters.spaced()
        cvwGallows.steps = viewModel.numErrors

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
            null -> updateViews()
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

    private fun toast(msg: CharSequence) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
