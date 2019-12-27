package thefun.bakery.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R
import thefun.bakery.Utils

class StoryWriteActivity : AppCompatActivity() {

    var storyEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_story_write)

        val resId = intent.getIntExtra("resId", 0)
        val bgUri = intent.getStringExtra("bgUri")

        if (resId != 0) {
            findViewById<ImageView>(R.id.feeling_preview).setImageResource(resId)
        }

        findViewById<ImageView>(R.id.story_close_btn).setOnClickListener {
            finish()
        }

//        val inputStream = contentResolver.openInputStream(Uri.parse(bgUri))
//        val img = BitmapFactory.decodeStream(inputStream)
//        inputStream.close()
//
//        val bg = findViewById<LinearLayout>(R.id.story_feeling_bg)
//        val cropped = Utils.scaleCenterCrop(img, bg.height, bg.width)
//
//        val blurred = Utils.blur(cropped, this)
//        if (blurred != null) {
//            bg.background = BitmapDrawable(blurred)
//        } else {
//            bg.background = BitmapDrawable(cropped)
//        }

        findViewById<TextView>(R.id.done_btn).setOnClickListener {
            val story = storyEditText?.text.toString()

            val newIntent = Intent()
            newIntent.putExtra("resId", resId)
            newIntent.putExtra("story", story)
            newIntent.putExtra("bgUri", bgUri)
            setResult(Activity.RESULT_OK, newIntent)
            finish()
        }

        storyEditText = findViewById(R.id.story_edit_text)
    }
}