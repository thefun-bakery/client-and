package thefun.bakery.activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R

class ImageSelectActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_select)

        val resId = intent.getIntExtra("resId", 0)
        if (resId != 0) {
            findViewById<ImageView>(R.id.feeling_preview).setImageResource(resId)
//            findViewById<TextView>(R.id.current_time).setText(System.currentTimeMillis())
        }

        findViewById<ImageView>(R.id.image_close_btn).setOnClickListener {
            finish()
        }
    }
}