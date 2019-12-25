package ru.cutepool.recipemanager

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recipe_list.view.*
import ru.cutepool.recipemanager.ui.adapters.ItemClickListener
import ru.cutepool.recipemanager.ui.adapters.RecipeAdapter
import ru.cutepool.recipemanager.viewmodels.MainViewModel


class RecipeListFragment : Fragment(), ItemClickListener{

    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        if (activity?.intent?.hasExtra(MainActivity().EXTRA_TITLE)!!) {
            val toolbar = view.toolbar
            toolbar.title = activity!!.intent.getStringExtra(MainActivity().EXTRA_TITLE)
            toolbar.setTitleTextColor(Color.WHITE)
        } else {
            val toolbar = view.toolbar
            toolbar.title = "Ты меня сломал"
        }

        initViews(view)
        if (activity?.intent?.hasExtra(MainActivity().EXTRA_URL)!!) {
            val extraUrl = activity!!.intent.getStringExtra(MainActivity().EXTRA_URL)
            Log.d("DEBUG", extraUrl)
            initViewModel(extraUrl)
        } else {
            Log.d("DEBUG", "Not extra")
            initViewModel()
        }

        return view
    }

    private fun initViews(view: View) {
        recipeAdapter = RecipeAdapter()
        recipeAdapter.setListener(this)

        with(view.rv_recipe_list) {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(view.context)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getRecipeData().observe(this, Observer { recipeAdapter.updateData(it) })
    }

    private fun initViewModel(extraUrl: String) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getRecipeData(extraUrl).observe(this, Observer { recipeAdapter.updateData(it) })
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(context, "${recipeAdapter.items[position].id}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, RecipeActivity::class.java)
        intent.putExtra(RecipeActivity().EXTRA_URL, "${MainActivity().hostIpAddress}recipe?id=${recipeAdapter.items[position].id}")
        intent.putExtra(RecipeActivity().EXTRA_IMAGE_URL, "${MainActivity().hostIpAddress}image?src=${recipeAdapter.items[position].image}")
        startActivity(intent)
    }
}

