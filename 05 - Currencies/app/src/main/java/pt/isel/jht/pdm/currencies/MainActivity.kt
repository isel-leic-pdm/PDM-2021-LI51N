package pt.isel.jht.pdm.currencies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val listView by lazy { findViewById<RecyclerView>(R.id.listView) }
    private val viewModel by viewModels<CurrenciesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CurrenciesAdapter(this)
        listView.adapter = adapter

        viewModel.currencies.observe(this) { quotes ->
            adapter.setItems(quotes)
        }
    }
}