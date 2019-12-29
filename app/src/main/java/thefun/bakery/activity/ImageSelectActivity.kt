package thefun.bakery.activity

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.naver.android.helloyako.imagecrop.view.ImageCropView
import thefun.bakery.R
import thefun.bakery.Utils
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class ImageSelectActivity: AppCompatActivity() {
    private var bgUri = ""
    private var height = 0
    private var width = 0
    var filePath = ""

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

        val clockHandler = ClockHandler()
        clockHandler.currentTextView = findViewById(R.id.current_time)
        clockHandler.sendEmptyMessage(0)

        findViewById<TextView>(R.id.image_done_btn).setOnClickListener{

            findViewById<ImageCropView>(R.id.feeling_bg).croppedImage?.let {
                bitmapConvertToFile(it)?.let {
                    filePath = it.absolutePath
                }
            }

            intent = Intent(this@ImageSelectActivity, StoryWriteActivity::class.java)
            intent.putExtra("resId", resId)
            intent.putExtra("bgUri", bgUri)
            intent.putExtra("width", width)
            intent.putExtra("height", height)
            intent.putExtra("filePath", filePath)
            startActivityForResult(intent, 1001)
        }
    }

    private class ClockHandler: Handler() {

        var currentTextView: TextView? = null

        override fun handleMessage(msg: Message?) {
            val sdf = SimpleDateFormat("yyyy. MM. dd  |  HH : mm : ss", Locale.getDefault())

            currentTextView?.text = sdf.format(Date())
            this.sendEmptyMessageDelayed(0, 1000)
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
                            newIntent.putExtra("filePath", it.getStringExtra("filePath"))
                            setResult(Activity.RESULT_OK, newIntent)
                            finish()
                        }
                    }

                    2000 -> {
                        data?.let {
                            bgUri = it.data.toString()

                            val inputStream = contentResolver.openInputStream(it.data)
                            val img = BitmapFactory.decodeStream(inputStream) as Bitmap
                            inputStream.close()

                            findViewById<LinearLayout>(R.id.text_bg).setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                            val bg = findViewById<ImageCropView>(R.id.feeling_bg)

//                            val cropped = Utils.scaleCenterCrop(img, bg.height, bg.width)

                            bg.setOnTouchListener(object : View.OnTouchListener {
                                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                    when (event?.action) {
                                        MotionEvent.ACTION_DOWN -> {
                                            findViewById<LinearLayout>(R.id.text_bg).visibility = View.GONE
                                            findViewById<View>(R.id.black_filter).visibility = View.GONE
                                        }
                                        MotionEvent.ACTION_UP -> {
                                            findViewById<LinearLayout>(R.id.text_bg).visibility = View.VISIBLE
                                            findViewById<View>(R.id.black_filter).visibility = View.VISIBLE
                                        }
                                    }
                                    return false
                                }
                            })

                            width = bg.width
                            height = bg.height

                            bg.setImageBitmap(img)

//                            bg.background = BitmapDrawable(cropped)
//                            val blurred = Utils.blur(cropped, this)
//                            if (blurred != null) {
//                                bg.background = BitmapDrawable(blurred)
//                            } else {
//                                bg.background = BitmapDrawable(cropped)
//                            }
                        }
                    }
                }
            }
        }
    }

    private fun bitmapConvertToFile(bitmap: Bitmap): File? {
        var fileOutputStream: FileOutputStream? = null
        var bitmapFile: File? = null
        try {
            val file = File(Environment.getExternalStoragePublicDirectory("image_crop_sample"), "")
            if (!file.exists()) {
                file.mkdir()
            }

            bitmapFile = File(file, "IMG_" + SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().time) + ".jpg")
            fileOutputStream = FileOutputStream(bitmapFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            MediaScannerConnection.scanFile(this, arrayOf(bitmapFile.absolutePath), null, object : MediaScannerConnection.MediaScannerConnectionClient {
                override fun onMediaScannerConnected() {

                }

                override fun onScanCompleted(path: String, uri: Uri) {
                    this@ImageSelectActivity?.runOnUiThread { Toast.makeText(this@ImageSelectActivity, "file saved", Toast.LENGTH_LONG).show() }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush()
                    fileOutputStream.close()
                } catch (e: Exception) {
                }

            }
        }

        return bitmapFile
    }
}