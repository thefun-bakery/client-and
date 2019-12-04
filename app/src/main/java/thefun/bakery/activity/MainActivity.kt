package thefun.bakery.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thefun.bakery.Const
import thefun.bakery.R
import thefun.bakery.api.ApiManager
import thefun.bakery.data.MainHome

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.main_title)?.let {
            it.text = "Kelly's Home"    //TODO remove
        }

        findViewById<View>(R.id.create_main_content).setOnClickListener {
            startActivity(Intent(this@MainActivity, FeelingSelectActivity::class.java))
        }

        ApiManager.api?.getMainHome()?.enqueue(object : Callback<MainHome> {
            override fun onFailure(call: Call<MainHome>, t: Throwable) {
                Log.e(Const.LOG, t.localizedMessage)
            }

            override fun onResponse(call: Call<MainHome>, response: Response<MainHome>) {
                response.body()?.let {
                    Log.e(Const.LOG, it.toString())

                    findViewById<TextView>(R.id.main_title)?.let { tv ->
                        tv.text = it.name
                    }

//                    findViewById<RelativeLayout>(R.id.main_home_background)
//                        .background = Color.
                }
            }
        })
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
