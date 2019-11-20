package thefun.bakery.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import thefun.bakery.MainApp
import thefun.bakery.R
import thefun.bakery.Urls
import thefun.bakery.Utils
import thefun.bakery.api.Api
import thefun.bakery.api.ApiClient

class MainActivity : AppCompatActivity() {

    private val permissionRequestCode = 1020

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.main_title)?.let {
            it.text = "Kelly's Home"    //TODO remove
        }

        findViewById<ImageView>(R.id.create_main_content).setOnClickListener {

        }

        Utils.checkPermission(this, Manifest.permission.INTERNET, permissionRequestCode)

//        ApiClient.loginKakao(application)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            permissionRequestCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                } else {
                    // permission denied
                }
            }
        }
    }
}
