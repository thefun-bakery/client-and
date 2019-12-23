package thefun.bakery.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
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
            startActivityForResult(Intent(this@MainActivity, FeelingSelectActivity::class.java), 1001)
        }

        findViewById<View>(R.id.tab_main_btn).setOnClickListener {
            it.tag = "on"
            findViewById<View>(R.id.tab_history_btn).tag = "off"
            findViewById<View>(R.id.tab_visitor_btn).tag = "off"
            setTabButton()
        }
        findViewById<View>(R.id.tab_history_btn).setOnClickListener {
            it.tag = "on"
            findViewById<View>(R.id.tab_main_btn).tag = "off"
            findViewById<View>(R.id.tab_visitor_btn).tag = "off"
            setTabButton()
        }
        findViewById<View>(R.id.tab_visitor_btn).setOnClickListener {
            it.tag = "on"
            findViewById<View>(R.id.tab_main_btn).tag = "off"
            findViewById<View>(R.id.tab_history_btn).tag = "off"
            setTabButton()
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

                    findViewById<TextView>(R.id.main_desc)?.let { tv ->
                        tv.text = it.desc
                    }

//                    findViewById<RelativeLayout>(R.id.main_home_background)
//                        .background = Color.
                }
            }
        })
    }

    private fun setTabButton() {
        if (findViewById<View>(R.id.tab_main_btn).tag == "on") {
            findViewById<View>(R.id.tab_main_btn).setBackgroundResource(R.drawable.rect_main_top_menu_on)
            findViewById<View>(R.id.tab_history_btn).setBackgroundResource(R.drawable.rect_main_top_menu_off)
            findViewById<View>(R.id.tab_visitor_btn).setBackgroundResource(R.drawable.rect_main_top_menu_off)
            findViewById<ImageView>(R.id.tab_main_icon).setImageResource(R.drawable.ic_tab_main_on)
            findViewById<ImageView>(R.id.tab_history_icon).setImageResource(R.drawable.ic_tab_history)
            findViewById<ImageView>(R.id.tab_visitor_icon).setImageResource(R.drawable.ic_tab_visitor)
            return
        }

        if (findViewById<View>(R.id.tab_history_btn).tag == "on") {
            findViewById<View>(R.id.tab_main_btn).setBackgroundResource(R.drawable.rect_main_top_menu_off)
            findViewById<View>(R.id.tab_history_btn).setBackgroundResource(R.drawable.rect_main_top_menu_on)
            findViewById<View>(R.id.tab_visitor_btn).setBackgroundResource(R.drawable.rect_main_top_menu_off)
            findViewById<ImageView>(R.id.tab_main_icon).setImageResource(R.drawable.ic_tab_main)
            findViewById<ImageView>(R.id.tab_history_icon).setImageResource(R.drawable.ic_tab_history_on)
            findViewById<ImageView>(R.id.tab_visitor_icon).setImageResource(R.drawable.ic_tab_visitor)
            return
        }

        if (findViewById<View>(R.id.tab_visitor_btn).tag == "on") {
            findViewById<View>(R.id.tab_main_btn).setBackgroundResource(R.drawable.rect_main_top_menu_off)
            findViewById<View>(R.id.tab_history_btn).setBackgroundResource(R.drawable.rect_main_top_menu_off)
            findViewById<View>(R.id.tab_visitor_btn).setBackgroundResource(R.drawable.rect_main_top_menu_on)
            findViewById<ImageView>(R.id.tab_main_icon).setImageResource(R.drawable.ic_tab_main)
            findViewById<ImageView>(R.id.tab_history_icon).setImageResource(R.drawable.ic_tab_history)
            findViewById<ImageView>(R.id.tab_visitor_icon).setImageResource(R.drawable.ic_tab_visitor_on)
            return
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1001 -> {
                data?.let {
                    val resId = it.getIntExtra("resId", 0)
                    val story = it.getStringExtra("story")

                    findViewById<ImageView>(R.id.feeling_im).setImageResource(resId)
                    findViewById<TextView>(R.id.main_desc).text = story
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
