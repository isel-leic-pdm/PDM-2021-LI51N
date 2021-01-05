package pt.isel.jht.pdm.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(), LocationListener {

    private val txtLatitude  by lazy { findViewById<TextView>(R.id.txtLatitude)  }
    private val txtLongitude by lazy { findViewById<TextView>(R.id.txtLongitude) }
    private val txtProviders by lazy { findViewById<TextView>(R.id.txtProviders) }

    private val locationManager by lazy { getSystemService(LOCATION_SERVICE) as LocationManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showProviders()
    }

    fun doStop(view: View) {
        locationManager.removeUpdates(this)
    }

    fun doStart(view: View) {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
            )

            return
        }

        requestLocationUpdates()
    }

    private fun showProviders() {
        txtProviders.text =
                locationManager.allProviders.map { provider ->
                    "$provider (${
                        locationManager.isProviderEnabled(provider).toEnabledString()
                    })"
                }.joinToString("\n")
    }

    private fun Boolean.toEnabledString() = "${if (this) "en" else "dis"}abled"

    override fun onLocationChanged(location: Location) {
        txtLatitude.text  = "%.4f".format(location.latitude)
        txtLongitude.text = "%.4f".format(location.longitude)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            // Consider calling shouldShowRequestPermissionRationale()

            Toast.makeText(this, "Allow access to fine location", Toast.LENGTH_SHORT).show()

            startActivity(
                    Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                    )
            )

            return
        }

        requestLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000,
                0.0F,
                this  // if the activity is destroyed (e.g. rotation), notifications will nor be properly delivered
        )
    }
}
