package pt.isel.jht.pdm.threading

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(NumsViewModel::class.java) }
    private val textView by lazy { findViewById<TextView>(R.id.textView) }

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.liveNums.observe(this) { num ->
            Log.d("Threading", "onGo on ui thread ${Thread.currentThread().id} ($num)")
            textView.text = num
        }
    }

    fun onGo1(view: View) {
        for (n in 0..9) {
            textView.text = n.toString()
            Thread.sleep(2000)
        }
        textView.text = "DONE!"
    }

    fun onGo2(view: View) {
        Log.d("Threading", "onGo on thread ${Thread.currentThread().id}")
        executor.execute {
            Log.d("Threading", "onGo executor on thread ${Thread.currentThread().id}")
            for (n in 0..9) {
                Log.d("Threading", "onGo executor on thread ${Thread.currentThread().id} ($n)")
                runOnUiThread {
                    Log.d("Threading", "onGo on ui thread ${Thread.currentThread().id} ($n)")
                    textView.text = n.toString()
                }
                Thread.sleep(2000)
            }
            runOnUiThread {
                Log.d("Threading", "onGo on ui thread ${Thread.currentThread().id}")
                textView.text = "DONE!"
            }
        }
        Log.d("Threading", "onGo finishing on thread ${Thread.currentThread().id}")
    }

    fun onGo3(view: View) {
        Log.d("Threading", "onGo on thread ${Thread.currentThread().id}")

        val mainHandler = Handler(mainLooper)

        executor.execute {
            Log.d("Threading", "onGo executor on thread ${Thread.currentThread().id}")
            for (n in 0..9) {
                Log.d("Threading", "onGo executor on thread ${Thread.currentThread().id} ($n)")
                mainHandler.post {
                    Log.d("Threading", "onGo on ui thread ${Thread.currentThread().id} ($n)")
                    textView.text = n.toString()
                }
                Thread.sleep(2000)
            }
            mainHandler.post {
                Log.d("Threading", "onGo on ui thread ${Thread.currentThread().id}")
                textView.text = "DONE!"
            }
        }
        Log.d("Threading", "onGo finishing on thread ${Thread.currentThread().id}")
    }

    fun onGo4(view: View) {

        val task = object : AsyncTask<Boolean, Int, String>() {

            override fun doInBackground(vararg params: Boolean?): String {
                val range = if (params[0] ?: false) 0..9 else 9 downTo 0
                for (n in range) {
                    publishProgress(n)
                    Thread.sleep(1000)
                }
                return "DONE!"
            }

            override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                textView.text = values[0]?.toString()
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                textView.text = result
            }
        }

        task.execute(true)
    }

    fun onGo5(view: View) {
        val liveNums = MutableLiveData<String>()

        liveNums.observe(this) { num ->
            Log.d("Threading", "onGo on ui thread ${Thread.currentThread().id} ($num)")
            textView.text = num
        }

        executor.execute {

            for (n in 0..9) {
                Log.d("Threading", "onGo on executor thread ${Thread.currentThread().id} ($n)")
                liveNums.postValue(n.toString())
                Thread.sleep(2000)
            }
            Log.d("Threading", "onGo on executor thread ${Thread.currentThread().id} (DONE)")
            liveNums.postValue("DONE!")

        }
    }

    fun onGo(view: View) {
        viewModel.startCounting()
    }
}

class NumsViewModel : ViewModel() {

    val executor = Executors.newSingleThreadExecutor()
    val liveNums = MutableLiveData<String>()

    fun startCounting() {
       executor.execute {
           for (n in 0..9) {
               Log.d("Threading", "counting on executor thread ${Thread.currentThread().id} ($n)")
               liveNums.postValue(n.toString())
               Thread.sleep(2000)
           }
           Log.d("Threading", "counting on executor thread ${Thread.currentThread().id} (DONE)")
           liveNums.postValue("DONE!")
       }
    }
}
