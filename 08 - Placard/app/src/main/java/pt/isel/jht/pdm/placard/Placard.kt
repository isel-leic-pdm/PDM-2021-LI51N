package pt.isel.jht.pdm.placard

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Placard(private val app: Application) {

    private val database = Firebase.firestore

    private val messages = database.collection("messages")

    private val placard = messages.document("placard")

    init {
        placard.addSnapshotListener { doc, ex ->
            if (ex != null) {
                Log.w("placard", "Listen failed: ${ex.message}", ex)
            } else if (doc != null && doc.exists()) {
                message.value = doc.toObject(PublicMessage::class.java)?.text
            }
        }
    }

    val message = MutableLiveData<String>()

    fun post(newMessage: String) {
        placard.set(PublicMessage(newMessage))
            .addOnSuccessListener {
                Toast.makeText(app, "POSTED", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { ex ->
                Toast.makeText(app, "FAILED", Toast.LENGTH_SHORT).show()
            }
    }
}

data class PublicMessage(var text: String? = null)

