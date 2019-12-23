package thefun.bakery.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R

class ImageSelectActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_select)

        val resId = intent.getIntExtra("resId", 0)
        if (resId != 0) {
            findViewById<ImageView>(R.id.feeling_preview).setImageResource(resId)
        }

        findViewById<ImageView>(R.id.image_close_btn).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.image_done_btn).setOnClickListener{
            intent = Intent(this@ImageSelectActivity, StoryWriteActivity::class.java)
            intent.putExtra("resId", resId)
            startActivityForResult(intent, 1001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val newIntent = Intent()
                    newIntent.putExtra("resId", it.getIntExtra("resId", 0))
                    newIntent.putExtra("story", it.getStringExtra("story"))
                    setResult(Activity.RESULT_OK, newIntent)
                    finish()
                }
            }
        }
    }
}