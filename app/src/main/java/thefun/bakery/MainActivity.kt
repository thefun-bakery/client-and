package thefun.bakery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.main_title)?.let {
            it.text = "Kelly's Home"    //TODO remove
        }

        findViewById<ImageView>(R.id.create_main_content).setOnClickListener {

        }
//        val testEditor = findViewById<EditText>(R.id.test_editor)
//        testEditor.setText("")
//        findViewById<ImageView>(R.id.main_content_bg)?.let {
//            it.setOnClickListener {
//                testEditor.performClick()
//            }
//        }
    }
}
