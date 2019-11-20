package thefun.bakery

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import thefun.bakery.api.ApiClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.main_title)?.let {
            it.text = "Kelly's Home"    //TODO remove
        }

        findViewById<ImageView>(R.id.create_main_content).setOnClickListener {

        }
    }
}
