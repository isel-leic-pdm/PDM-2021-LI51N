package pt.isel.jht.pdm.currencies

import android.app.Application
import com.android.volley.toolbox.Volley

class CurrenciesApp : Application() {

    val requestQueue by lazy { Volley.newRequestQueue(this) }

}

val Application.requestQueue
    get() = (this as CurrenciesApp).requestQueue
