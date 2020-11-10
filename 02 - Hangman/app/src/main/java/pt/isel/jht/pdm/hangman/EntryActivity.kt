package pt.isel.jht.pdm.hangman

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EntryActivity : AppCompatActivity() {

    private val listView by lazy { findViewById<RecyclerView>(R.id.listView) }

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(HangmanHistoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val historyAdapter = HangmanHistoryAdapter(this)
        listView.adapter = historyAdapter
        listView.layoutManager = LinearLayoutManager(this)

        viewModel.history.observe(this, { items ->
            historyAdapter.setItems(items)
        })
    }

    fun onNewGame(view: View) {
        startActivity(Intent(this, HangmanActivity::class.java))
    }
}
