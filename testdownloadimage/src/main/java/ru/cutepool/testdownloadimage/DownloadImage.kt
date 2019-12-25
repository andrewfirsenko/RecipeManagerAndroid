package ru.cutepool.testdownloadimage

import android.graphics.Bitmap
import android.os.AsyncTask
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView

class DownloadImage(bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

    val bmImage: ImageView

    init {
        this.bmImage = bmImage
    }

    override fun doInBackground(vararg params: String?): Bitmap? {
        val urlDisplay = params[0]
        var mIcon11: Bitmap? = null
        try {
            val inputStream = java.net.URL(urlDisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("IMAGE", e.message)
            e.printStackTrace()
        }

        return mIcon11
    }

    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }
}