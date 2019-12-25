package ru.cutepool.recipemanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recipe.*
import ru.cutepool.recipemanager.ui.adapters.RecipeIngredientsAdapter
import ru.cutepool.recipemanager.ui.adapters.RecipeStepsAdapter
import ru.cutepool.recipemanager.viewmodels.MainViewModel

class RecipeActivity : AppCompatActivity() {
    val EXTRA_URL = "ru.cutepool.recipeactivity.extra_url"
    val EXTRA_IMAGE_URL = "ru.cutepool.recipeactivity.extra_image_url"

    private lateinit var ingredientsAdapter: RecipeIngredientsAdapter
    private lateinit var stepsAdapter: RecipeStepsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)


        if (intent.hasExtra(EXTRA_IMAGE_URL)) {
            DownloadImage(iv_backdrop).execute(intent.getStringExtra(EXTRA_IMAGE_URL))
        }
        initViews()
        if (intent.hasExtra(EXTRA_URL)) {
            initViewModel(intent.getStringExtra(EXTRA_URL))
        }

//        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
//        toolbar.title = titleRecipe
        tv_title.text = viewModel.getRecipeTitle()
    }

    private fun initViews() {
        ingredientsAdapter = RecipeIngredientsAdapter()
        stepsAdapter = RecipeStepsAdapter()

        with(rv_recipe_ingredients) {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(this@RecipeActivity)
        }

        with(rv_recipe_steps) {
            adapter = stepsAdapter
            layoutManager = LinearLayoutManager(this@RecipeActivity)
        }
    }

    private fun initViewModel(extraUrl: String) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getRecipeDataIngredients(extraUrl).observe(this, Observer { ingredientsAdapter.updateData(it) })
        viewModel.getRecipeDataSteps(extraUrl).observe(this, Observer { stepsAdapter.updateData(it) })
    }

    fun description(v: View?) {

    }
}