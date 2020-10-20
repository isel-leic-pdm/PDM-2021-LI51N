package pt.isel.jht.pdm.hello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
class MainActivity : AppCompatActivity() {

    private val HELLO_MSG_KEY = "HELLO_MSG_KEY";

    private val txtHello by lazy { findViewById<TextView>(R.id.txtHello) }
    private val butMsg1 by lazy { findViewById<Button>(R.id.butMsg1) }
    private val butMsg2 by lazy { findViewById<Button>(R.id.butMsg2) }

    private val msg1 by lazy { getString(R.string.message1) }
    private val msg2 by lazy { getString(R.string.message2) }

    private var curr_msg_idx = 0

    private fun updateMsg() {
        val msg = if (curr_msg_idx == 0) msg1 else msg2
        txtHello.text = msg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity::onCreate", "onCreate for ${this.hashCode()}")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateMsg()

        butMsg1.setOnClickListener { curr_msg_idx = 0; updateMsg() }
        butMsg2.setOnClickListener { curr_msg_idx = 1; updateMsg() }
    }

    override fun onStart() {
        Log.d("MainActivity::onStart", "onStart for ${this.hashCode()}")
        super.onStart()
    }

    override fun onResume() {
        Log.d("MainActivity::onResume", "onResume for ${this.hashCode()}")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MainActivity::onPause", "onPause for ${this.hashCode()}")
        super.onPause()
    }

    override fun onStop() {
        Log.d("MainActivity::onStop", "onStop for ${this.hashCode()}")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MainActivity::onDestroy", "onDestroy for ${this.hashCode()}")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("MainActivity::onSave", "onSaveInstanceState for ${this.hashCode()}")
        super.onSaveInstanceState(outState)
        outState.putInt(HELLO_MSG_KEY, curr_msg_idx)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d("MainActivity::onRestore", "onRestoreInstanceState for ${this.hashCode()}")
        super.onRestoreInstanceState(savedInstanceState)
        curr_msg_idx = savedInstanceState.getInt(HELLO_MSG_KEY)
        updateMsg()
    }
}
*/

class MainActivity : AppCompatActivity() {

    private val txtHello by lazy { findViewById<TextView>(R.id.txtHello) }
    private val butMsg1 by lazy { findViewById<Button>(R.id.butMsg1) }
    private val butMsg2 by lazy { findViewById<Button>(R.id.butMsg2) }

    private val msg1 by lazy { getString(R.string.message1) }
    private val msg2 by lazy { getString(R.string.message2) }

    private val model by lazy { ViewModelProvider(this).get(MessageViewModel::class.java) }

    private fun updateMsg() {
        val msg = if (model.curr_msg_idx == 0) msg1 else msg2
        txtHello.text = msg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity::onCreate", "onCreate for ${this.hashCode()}")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateMsg()

        butMsg1.setOnClickListener { model.curr_msg_idx = 0; updateMsg() }
        butMsg2.setOnClickListener { model.curr_msg_idx = 1; updateMsg() }
    }

    override fun onStart() {
        Log.d("MainActivity::onStart", "onStart for ${this.hashCode()}")
        super.onStart()
    }

    override fun onResume() {
        Log.d("MainActivity::onResume", "onResume for ${this.hashCode()}")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MainActivity::onPause", "onPause for ${this.hashCode()}")
        super.onPause()
    }

    override fun onStop() {
        Log.d("MainActivity::onStop", "onStop for ${this.hashCode()}")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MainActivity::onDestroy", "onDestroy for ${this.hashCode()}")
        super.onDestroy()
    }
}

class MessageViewModel : ViewModel() {
    var curr_msg_idx = 0
}