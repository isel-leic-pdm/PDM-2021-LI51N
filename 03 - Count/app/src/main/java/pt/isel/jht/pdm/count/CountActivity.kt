package pt.isel.jht.pdm.count

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.jht.pdm.count.databinding.ActivityCountBinding

class CountActivity : AppCompatActivity() {

    private val bindings by lazy { ActivityCountBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindings.root)

        val initial_count = intent.getLongExtra("COUNTER", 10)
        // bindings.txtCount.text = initial_count.toString()

        viewModel.tryStartCount(initial_count)

        viewModel.counter.observe(this) { count ->
            val txt = if (count < 0) "DONE" else count.toString()
            bindings.txtCount.text = txt
        }
    }
}


class CountViewModel : ViewModel() {

    private var started = false

    val counter = MutableLiveData<Long>()

    fun tryStartCount(initial_count: Long) {
        if (!started) {

            val timer = object : CountDownTimer(initial_count * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val count = (millisUntilFinished + 500) / 1000
                    counter.value = count
                }

                override fun onFinish() {
                    counter.value = -1
                }
            }

            started = true
            timer.start()

        }
    }
}
