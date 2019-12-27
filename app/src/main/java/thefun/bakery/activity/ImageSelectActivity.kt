package thefun.bakery.activity

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import thefun.bakery.R
import thefun.bakery.Utils


class ImageSelectActivity: AppCompatActivity() {
    private var bgUri = ""

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

        findViewById<LinearLayout>(R.id.gallery_btn).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent, 2000)
        }

        findViewById<LinearLayout>(R.id.camera_btn).setOnClickListener {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//            try {
//                tempFile = createImageFile()
//            } catch (e: IOException) {
//                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
//                finish()
//                e.printStackTrace()
//            }
//
//            if (tempFile != null) {
//
//                val photoUri = Uri.fromFile(tempFile)
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                startActivityForResult(intent, PICK_FROM_CAMERA)
//            }
        }

        findViewById<TextView>(R.id.image_done_btn).setOnClickListener{
            intent = Intent(this@ImageSelectActivity, StoryWriteActivity::class.java)
            intent.putExtra("resId", resId)
            intent.putExtra("bgUri", bgUri)
            startActivityForResult(intent, 1001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    1001 -> {
                        data?.let {
                            val newIntent = Intent()
                            newIntent.putExtra("resId", it.getIntExtra("resId", 0))
                            newIntent.putExtra("story", it.getStringExtra("story"))
                            newIntent.putExtra("bgUri", it.getStringExtra("bgUri"))
                            setResult(Activity.RESULT_OK, newIntent)
                            finish()
                        }
                    }

                    2000 -> {
                        data?.let {
                            bgUri = it.data.toString()

                            val inputStream = contentResolver.openInputStream(it.data)
                            val img = BitmapFactory.decodeStream(inputStream)
                            inputStream.close()

                            val bg = findViewById<LinearLayout>(R.id.feeling_bg)
                            val cropped = Utils.scaleCenterCrop(img, bg.height, bg.width)

                            val blurred = Utils.blur(cropped, this)
                            if (blurred != null) {
                                bg.background = BitmapDrawable(blurred)
                            } else {
                                bg.background = BitmapDrawable(cropped)
                            }
                        }
                    }
                }
            }
        }
    }
}