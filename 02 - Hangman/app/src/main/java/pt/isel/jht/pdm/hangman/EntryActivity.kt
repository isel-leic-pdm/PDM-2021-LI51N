package pt.isel.jht.pdm.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
    }

    fun onNewGame(view: View) {
        startActivity(Intent(this, HangmanActivity::class.java))
    }
}
