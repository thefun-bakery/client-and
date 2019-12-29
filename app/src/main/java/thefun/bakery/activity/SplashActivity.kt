package thefun.bakery.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import thefun.bakery.BuildConfig
import thefun.bakery.Const
import thefun.bakery.R
import thefun.bakery.api.ApiManager
import java.util.concurrent.TimeUnit


class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

//        Utils.checkPermission(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET), Const.PerminssionRequestCode)

        val pref = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        val accessToken = pref.getString(Const.APP_ACCESS_TOKEN, "")

        if (accessToken.isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            Observable.just(accessToken)
                .delay(1000L, TimeUnit.MILLISECONDS)
                .flatMap {
                    ApiManager.api?.isLogin()
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isLogin) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    finish()
                }, {
                    Log.e("###", it.localizedMessage)
                })

        }

        checkPermission()
    }

    fun checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)) != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.INTERNET)) {
                // If we should give explanation of requested permissions

//                // Show an alert dialog here with request explanation
//                val builder = AlertDialog.Builder(mActivity)
//                builder.setMessage("Camera, Read Contacts and Write External" + " Storage permissions are required to do the task.")
//                builder.setTitle("Please grant those permissions")
//                builder.setPositiveButton(
//                    "OK",
//                    DialogInterface.OnClickListener { dialogInterface, i ->
//                        ActivityCompat.requestPermissions(
//                            mActivity,
//                            arrayOf(
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.READ_CONTACTS,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                            ),
//                            MY_PERMISSIONS_REQUEST_CODE
//                        )
//                    })
//                builder.setNeutralButton("Cancel", null)
//                val dialog = builder.create()
//                dialog.show()
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                    ), 1000)
            }
        } else {
            // Do something, when permissions are already granted
//            Toast.makeText(mContext, "Permissions already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            Const.PerminssionRequestCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                } else {
                    // permission denied
                }
            }
        }
    }
}