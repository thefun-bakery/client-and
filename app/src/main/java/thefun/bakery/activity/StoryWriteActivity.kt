package thefun.bakery.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R

class StoryWriteActivity : AppCompatActivity() {

    var storyEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_story_write)

        val resId = intent.getIntExtra("resId", 0)
        if (resId != 0) {
            findViewById<ImageView>(R.id.feeling_preview).setImageResource(resId)
        }

        findViewById<ImageView>(R.id.story_close_btn).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.done_btn).setOnClickListener {
            val story = storyEditText?.text.toString()

            val newIntent = Intent()
            newIntent.putExtra("resId", resId)
            newIntent.putExtra("story", story)
            setResult(Activity.RESULT_OK, newIntent)
            finish()
        }

        storyEditText = findViewById(R.id.story_edit_text)
    }
}