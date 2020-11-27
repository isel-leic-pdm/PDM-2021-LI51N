package pt.isel.jht.pdm.currencies

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrenciesViewModel(private val app : Application) : AndroidViewModel(app) {

    val currencies = MutableLiveData<List<ExchangeRate>>()

    init {
        updateCurrencies()
    }

    fun updateCurrencies() =
        Currencies.loadCurrencies(
            app.requestQueue,
            { rates ->
                currencies.value = rates
            },
            { errorMessage ->
                Toast.makeText(app, "Update failed: $errorMessage", Toast.LENGTH_LONG).show()
            }
        )
}
