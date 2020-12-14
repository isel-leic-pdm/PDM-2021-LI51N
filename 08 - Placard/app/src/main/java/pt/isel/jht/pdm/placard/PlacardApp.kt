package pt.isel.jht.pdm.placard

import android.app.Application

class PlacardApp : Application() {

    val placard by lazy { Placard(this) }

}

val Application.placard : Placard
    get() = (this as PlacardApp).placard
