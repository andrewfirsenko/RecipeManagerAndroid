package ru.cutepool.recipemanager


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.spinner.view.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val EXTRA_URL = "ru.cutepool.recipemanager.extra_url"
    val EXTRA_TITLE = "ru.cutepool.recipemanager.extra_title"
    val hostIpAddress = "http://45.90.32.228/"
    private val urlAllIngredients = "${hostIpAddress}ingredients"
    private val listIngredients = ArrayList<String>()
    private val titleSpinner = "Найти по продукту"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.title = "Другое"
//        Log.d("DEBUG", toolbar.title.toString())

        val jsonResponse = SendDoGet().execute(urlAllIngredients).get()

        try {
            val jsonObject = JSONObject(jsonResponse)
            val jsonArray = jsonObject.getJSONArray("names")
            Log.d("ARRAY", jsonArray.toString())
            Log.d("ARRAY", jsonArray.length().toString())
            for (i: Int in 1..jsonArray.length()) {
                listIngredients.add(jsonArray.getString(i-1))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val adapter = MyAdapter(this.applicationContext)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            private var can = false

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (can) {
                    val intent = Intent(applicationContext, RecipeListActivity::class.java)
                    intent.putExtra(EXTRA_URL, "${hostIpAddress}recipes?ingredient=${listIngredients[position]}")
                    intent.putExtra(EXTRA_TITLE, listIngredients[position])
                    startActivity(intent)
//                    finish()
                } else {
                    can = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        breakfast.setOnClickListener(clickListener)
        lunch.setOnClickListener(clickListener)
        dinner.setOnClickListener(clickListener)
        dessert.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener {
        val intent = Intent(applicationContext, RecipeListActivity::class.java)
        val categoryExtra = when (it.id) {
            R.id.breakfast -> "BREAKFAST"
            R.id.lunch -> "LUNCH"
            R.id.dinner -> "DINNER"
            R.id.dessert -> "DESSERT"
            else -> "ERROR"
        }
        intent.putExtra(EXTRA_URL, "${hostIpAddress}recipes?category=${categoryExtra}")
        val categoryExtraRus = when (it.id) {
            R.id.breakfast -> "Завтраки"
            R.id.lunch -> "Обеды"
            R.id.dinner -> "Ужины"
            R.id.dessert -> "Десерты"
            else -> "ERROR"
        }
        intent.putExtra(EXTRA_TITLE, categoryExtraRus)
        startActivity(intent)
//        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private inner class MyAdapter : ArrayAdapter<String> {

        constructor(context: Context) : super(context, R.layout.spinner, R.id.spinnerText, listIngredients)

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//            Log.d("DEBUG", "getDrop $position")
            if (position == 0) {
                convertView?.spinnerText?.text = listIngredients[position]
            }
            return super.getDropDownView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return if (position == 0) {
                val view = super.getView(position, convertView, parent)
                view.spinnerText.text = titleSpinner
                view.spinnerText.setTextColor(Color.parseColor("#73FFFFFF"))
                view.spinnerText.setBackgroundResource(R.drawable.spinner_background)
                view.spinnerText.textSize = 24F
                view.spinnerText.updatePadding(0, 7, 0, 7)
                view.spinnerText.gravity = Gravity.CENTER_HORIZONTAL
                view
            } else {
                super.getView(position, convertView, parent)
            }
        }
    }
}