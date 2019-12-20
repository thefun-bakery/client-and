package thefun.bakery.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R

class FeelingPreviewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feelin_preview)

        val resId = intent.getIntExtra("resId", 0)
        if (resId != 0) {
            findViewById<ImageView>(R.id.imageView).setImageResource(resId)
        }
    }
}