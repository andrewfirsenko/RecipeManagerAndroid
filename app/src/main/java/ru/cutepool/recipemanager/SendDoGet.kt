package ru.cutepool.recipemanager

import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStreamReader


class SendDoGet : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String? {
        var result = ""
        try {
            val url = URL(params[0])
            val con = url.openConnection() as HttpURLConnection
            if (con.responseCode == HttpURLConnection.HTTP_OK) {
                val input = con.getInputStream()
                val isw = InputStreamReader(input)
                var data = isw.read()
                while (data != -1) {
                    val current = data.toChar()
                    data = isw.read()
                    result += current
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}