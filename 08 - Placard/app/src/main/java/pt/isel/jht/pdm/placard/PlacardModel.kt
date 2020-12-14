package pt.isel.jht.pdm.placard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PlacardModel(app: Application) : AndroidViewModel(app) {
    private val placard = app.placard

    val message : LiveData<String>
        get() = placard.message

    fun post(message: String) {
        placard.post(message)
    }
}
