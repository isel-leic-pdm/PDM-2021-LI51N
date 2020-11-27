package pt.isel.jht.pdm.currencies

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

data class ExchangeRate(
    val sourceCurrency : String,
    val targetCurrency : String,
    val rate : Double
)

object Currencies {

    private val uri = "http://api.currencylayer.com/live?" +
            "access_key=a76c13f17a0d445356c0f4d36738fe92" +
            "&source=USD" +
            "&format=1"

    fun loadCurrencies(
        queue : RequestQueue,
        onSuccess : (List<ExchangeRate>) -> Unit,
        onError : (String) -> Unit
    ) {
        queue.add(
            JsonObjectRequest(
                Request.Method.GET, uri, null,
                { result ->
                    onSuccess(convertResult(result))
                },
                { error ->
                    onError(error.message ?: "ERROR")
                }
            )
        )
    }

    private fun convertResult(obj : JSONObject) : List<ExchangeRate> {
        val rates = mutableListOf<ExchangeRate>()
        val quotes = obj.getJSONObject("quotes")
        for (curr in quotes.keys()) {
            rates.add(ExchangeRate(curr.take(3), curr.takeLast(3), quotes.getDouble(curr)))
        }
        return rates
    }
}
