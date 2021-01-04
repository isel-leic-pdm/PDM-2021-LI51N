package pt.isel.jht.pdm.toastservice

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.util.concurrent.Executors

class ToastService : Service() {

    companion object {
        val SERVICE_CHANNEL_ID = "toast.service.channel"
    }

    private val executor by lazy { Executors.newSingleThreadExecutor() }
    private val mainHandler by lazy { Handler(mainLooper) }

    @Volatile
    private var stopRequested = false

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("ToastService", "Service starting...")

        startForeground(
            1,  // must not be 0
            notificationBuilder(applicationContext, SERVICE_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Toasting in background")
                .setContentText("This app toasts periodically")
                .setOngoing(true)
                .build()
        )

        executor.submit {
            while (!stopRequested) {
                Thread.sleep(5000)
                Log.i("ToastService", "Service toasting...")
                mainHandler.post {
                    Toast.makeText(this, "TICK", Toast.LENGTH_SHORT).show()
                }
            }
            Log.i("ToastService", "Service ending...")
            stopForeground(true)
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.i("ToastService", "Service stopping...")
        super.onDestroy()
        stopRequested = true
    }

    fun notificationBuilder(context: Context, channelId: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
        } else {
            Notification.Builder(context)
        }
}
