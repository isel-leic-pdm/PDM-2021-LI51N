package pt.isel.jht.pdm.work

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.work.*

class MainActivity : AppCompatActivity() {

    private val textView by lazy { findViewById<TextView>(R.id.textView) }

    private val workManager by lazy { WorkManager.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onGo(view: View) {

        val workRequest =
            OneTimeWorkRequestBuilder<UselessWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiresBatteryNotLow(true)
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .build()
                )
                .build()

        Log.i("Working", "WorkRequest ${workRequest.id} in thread ${Thread.currentThread().id}")
        workManager.enqueue(workRequest)

    }
}

class UselessWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val mainHandler = Handler(context.mainLooper)

    override fun doWork(): Result {

        for (n in 0..3) {
            val msg = "STEP $n in thread ${Thread.currentThread().id}"
            mainHandler.post {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
            Log.i("Working", msg)
            Thread.sleep(3000)
        }

        return Result.success()
    }
}
