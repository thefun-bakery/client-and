package thefun.bakery

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.renderscript.Allocation
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.RenderScript
import android.graphics.Bitmap
import android.graphics.Canvas
import android.renderscript.Element
import android.graphics.RectF




object Utils {

    fun checkPermission(activity: Activity, permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
            return false
        } else {
            return true
        }
    }

    fun blur(image: Bitmap, context: Context): Bitmap {
        val BITMAP_SCALE = 0.4f
        val BLUR_RADIUS = 15.5f
        val width = Math.round(image.width * BITMAP_SCALE)
        val height = Math.round(image.height * BITMAP_SCALE)
        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)
        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(BLUR_RADIUS)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }

    fun scaleCenterCrop(source: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val sourceWidth = source.width
        val sourceHeight = source.height

        val xScale = newWidth.toFloat() / sourceWidth
        val yScale = newHeight.toFloat() / sourceHeight
        val scale = Math.max(xScale, yScale)

        // Now get the size of the source bitmap when scaled
        val scaledWidth = scale * sourceWidth
        val scaledHeight = scale * sourceHeight

        val left = (newWidth - scaledWidth) / 2
        val top = (newHeight - scaledHeight) / 2

        val targetRect =
            RectF(left, top, left + scaledWidth, top + scaledHeight)//from ww w  .j a va 2s. co m

        val dest = Bitmap.createBitmap(
            newWidth, newHeight,
            source.config
        )
        val canvas = Canvas(dest)
        canvas.drawBitmap(source, null, targetRect, null)

        return dest
    }
}