package pt.isel.jht.pdm.hangman

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pt.isel.jht.pdm.hangman.databinding.ActivityHangmanBinding

class HangmanActivity : AppCompatActivity() {

    private val views by lazy { ActivityHangmanBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this).get(HangmanViewModel::class.java) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)

        updateViews()

        views.butGuess.setOnClickListener { onGuess() }

        viewModel.history.observe(this, Observer { items ->
            Toast.makeText(this, "History items: ${items.size}", Toast.LENGTH_LONG).show()
            items.forEach { item -> Log.d("Hangman::history", "${item.id} : ${item}") }
        })

        var origin : Point? = null
        views.cvwGallows.setOnTouchListener { _, event ->
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> { toast("DOWN ($x, $y)"); origin = Point(x, y) }
                MotionEvent.ACTION_MOVE -> { /* toast("MOVE ($x, $y)") */ origin?.let { orig -> drawLine(Line(orig, Point(x, y)))}}
                MotionEvent.ACTION_UP   -> { toast("UP ($x, $y)");   origin?.let { orig -> saveLine(Line(orig, Point(x, y))) } }
            }
            true
        }
    }

    private fun saveLine(line: Line) {
        drawLine(line)
        viewModel.line = views.cvwGallows.line
    }

    private fun drawLine(line: Line) {
        with (views) {
            cvwGallows.line = Line(
                Point(
                    line.begin.x / cvwGallows.width,
                    line.begin.y / cvwGallows.height
                ),
                Point(
                    line.end.x / cvwGallows.width,
                    line.end.y / cvwGallows.height
                )
            )
        }
    }

    private fun updateViews() {
        with (views) {
            txtWord.text = viewModel.visibleWord.spaced()
            txtWrong.text = viewModel.wrongLetters.spaced()
            cvwGallows.steps = viewModel.numErrors
            cvwGallows.line = viewModel.line

            viewModel.gameResultMessage?.let { gameResultMessage ->
                txtResult.text = gameResultMessage
                edtLetter.isEnabled = false
                butGuess.isEnabled = false
                viewModel.saveGame()
            }
        }
    }

    private fun onGuess() {
        val guess = views.edtLetter.text.toString()
        when (val errorMsg = viewModel.guessWith(guess)) {
            null -> updateViews()
            else -> Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
        }
        views.edtLetter.text.clear()
    }

    private fun String.spaced() = this.toCharArray().joinToString(" ")

    private fun toast(msg: CharSequence) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
