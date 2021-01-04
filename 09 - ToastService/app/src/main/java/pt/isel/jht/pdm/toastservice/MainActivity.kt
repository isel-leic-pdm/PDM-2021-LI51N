package pt.isel.jht.pdm.toastservice

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun doStart(view: View) {
        doStartService(Intent(this, ToastService::class.java))
    }

    fun doStop(view: View) {
        stopService(Intent(this, ToastService::class.java))
    }

    private fun doStartService(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}