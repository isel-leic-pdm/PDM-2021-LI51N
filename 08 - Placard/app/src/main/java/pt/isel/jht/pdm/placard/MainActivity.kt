package pt.isel.jht.pdm.placard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val model by viewModels<PlacardModel>()

    private val txtMessage by lazy { findViewById<TextView>(R.id.txtMessage) }
    private val edtMessage by lazy { findViewById<EditText>(R.id.edtMessage) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.message.observe(this) { message ->
            txtMessage.text = message
        }
    }

    fun doPublish(view: View) {
        model.post(edtMessage.text.toString())
    }
}