package thefun.bakery.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.icu.util.Calendar
import android.net.Uri
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
import android.os.Handler
import android.os.Message
import android.widget.ImageButton
import android.widget.LinearLayout
import thefun.bakery.Utils
import java.text.SimpleDateFormat
import java.util.*


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

        findViewById<ImageButton>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
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

        findViewById<ImageView>(R.id.hug).tag = "off"
        findViewById<ImageView>(R.id.hug).setOnClickListener {
            if (it.tag == "off") {
                findViewById<ImageView>(R.id.hug).setImageResource(R.drawable.ic_hug_off)
                it.tag ="on"
            } else {
                findViewById<ImageView>(R.id.hug).setImageResource(R.drawable.ic_hug_on)
                it.tag ="off"
            }
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
                }
            }
        })

        val clockHandler = ClockHandler()
        clockHandler.currentTextView = findViewById(R.id.current_time)
        clockHandler.sendEmptyMessage(0)
    }

    private class ClockHandler: Handler() {

        var currentTextView: TextView? = null

        override fun handleMessage(msg: Message?) {
            val sdf = SimpleDateFormat("yyyy. MM. dd  |  HH : mm : ss", Locale.getDefault())

            currentTextView?.text = sdf.format(Date())
            this.sendEmptyMessageDelayed(0, 1000)
        }
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
                    val bgUri = it.getStringExtra("bgUri")

                    findViewById<ImageView>(R.id.feeling_im).setImageResource(resId)
                    if (story.isNullOrEmpty()) {
                        findViewById<TextView>(R.id.emotion_msg).visibility = View.GONE
                    } else {
                        findViewById<TextView>(R.id.emotion_msg).visibility = View.VISIBLE
                        findViewById<TextView>(R.id.emotion_msg).text = story
                    }

                    if (!bgUri.isNullOrEmpty()) {
                        val inputStream = contentResolver.openInputStream(Uri.parse(bgUri))
                        val img = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()

                        val bg = findViewById<ImageView>(R.id.main_content_bg)
                        val cropped = Utils.scaleCenterCrop(img, bg.height, bg.width)

                        val blurred = Utils.blur(cropped, this)
                        if (blurred != null) {
                            bg.setImageBitmap(blurred)
                        } else {
                            bg.setImageBitmap(cropped)
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
