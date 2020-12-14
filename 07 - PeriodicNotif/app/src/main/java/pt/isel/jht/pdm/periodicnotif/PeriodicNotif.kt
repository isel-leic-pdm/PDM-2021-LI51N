package pt.isel.jht.pdm.periodicnotif

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Process
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

class PeriodicNotifApp : Application() {

    companion object {
        val DEMO_CHANNEL_ID = "demo.channel"
    }

    private val workManager by lazy { WorkManager.getInstance(this) }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel()
        }

        setupPeriodicWork()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannel() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.getNotificationChannel(DEMO_CHANNEL_ID) == null) {
            val notificationChannel = NotificationChannel(DEMO_CHANNEL_ID, "Demo Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableLights(false)
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun setupPeriodicWork() {

        val workRequest =
            PeriodicWorkRequestBuilder<DemoWorker>(15, TimeUnit.MINUTES)
                .build()

        // for demo only
        workManager.cancelUniqueWork("periodic-demo")

        workManager.enqueueUniquePeriodicWork(
            "periodic-demo",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}

class DemoWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val mainHandler = Handler(context.mainLooper)

    private var count = 0

    override fun doWork(): Result {

        for (n in 0..4) {

            val newCount = count + 1
            count = newCount

            val text = "PROC: ${Process.myPid()} COUNT: $newCount TIME: ${Date().time}"

            val notification =
                getNotificationBuilder(context, PeriodicNotifApp.DEMO_CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.star_on)
                    .setContentTitle("Demo notification")
                    .setContentText(text)
                    .build()

            mainHandler.post {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                notificationManager.notify(0, notification)
            }

            Thread.sleep(10000)
        }

        return Result.success()
    }

    fun getNotificationBuilder(context: Context, channelId: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
        } else {
            Notification.Builder(context)
        }

}
