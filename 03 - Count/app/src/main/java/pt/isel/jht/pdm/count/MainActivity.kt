package pt.isel.jht.pdm.count

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pt.isel.jht.pdm.count.databinding.ActivityMainBinding
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private val bindings by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindings.root)
    }

    fun onButtonStart(view: View) {
        try {
            val count = edtCount.text.toString().toLong()
            if (count < 1 || count > 99) {
                Log.w("Count::onButtonStart", "Value out of range for count")
                Toast.makeText(this, "Value out of range", Toast.LENGTH_SHORT).show()
            } else {
                launchCountingActivity(count)
            }
        } catch (ex: NumberFormatException) {
            Log.w("Count::onButtonStart", "Invalid input for count")
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchCountingActivity(count: Long) {
        val intent = Intent(this, CountActivity::class.java)
        intent.putExtra("COUNTER",count)
        startActivity(intent)
    }
}