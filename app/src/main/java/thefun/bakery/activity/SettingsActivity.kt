package thefun.bakery.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R

class SettingsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        findViewById<ImageView>(R.id.out_btn).setOnClickListener {
            finish()
        }
    }
}