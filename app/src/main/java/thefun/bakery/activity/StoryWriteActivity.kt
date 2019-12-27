package thefun.bakery.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R
import thefun.bakery.Utils
import java.text.SimpleDateFormat
import java.util.*

class StoryWriteActivity : AppCompatActivity() {

    var storyEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_story_write)

        val resId = intent.getIntExtra("resId", 0)
        val bgUri = intent.getStringExtra("bgUri")
        val bgWidth = intent.getIntExtra("width", 500)
        val bgHeight = intent.getIntExtra("height", 500)

        if (resId != 0) {
            findViewById<ImageView>(R.id.feeling_preview).setImageResource(resId)
        }

        findViewById<ImageView>(R.id.story_close_btn).setOnClickListener {
            finish()
        }

        if (!bgUri.isNullOrEmpty()) {
            val inputStream = contentResolver.openInputStream(Uri.parse(bgUri))
            val img = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            val bg = findViewById<LinearLayout>(R.id.story_feeling_bg)
            val cropped = Utils.scaleCenterCrop(img, bgHeight, bgWidth)

            val blurred = Utils.blur(cropped, this)
            if (blurred != null) {
                bg.background = BitmapDrawable(blurred)
            } else {
                bg.background = BitmapDrawable(cropped)
            }
        }

        findViewById<TextView>(R.id.done_btn).setOnClickListener {
            val story = storyEditText?.text.toString()

            val newIntent = Intent()
            newIntent.putExtra("resId", resId)
            newIntent.putExtra("story", story)
            newIntent.putExtra("bgUri", bgUri)
            setResult(Activity.RESULT_OK, newIntent)
            finish()
        }

        val clockHandler = ClockHandler()
        clockHandler.currentTextView = findViewById(R.id.current_time)
        clockHandler.sendEmptyMessage(0)

        storyEditText = findViewById(R.id.story_edit_text)
        storyEditText?.requestFocus()
    }

    private class ClockHandler: Handler() {

        var currentTextView: TextView? = null

        override fun handleMessage(msg: Message?) {
            val sdf = SimpleDateFormat("yyyy. MM. dd  |  HH : mm : ss", Locale.getDefault())

            currentTextView?.text = sdf.format(Date())
            this.sendEmptyMessageDelayed(0, 1000)
        }
    }
}