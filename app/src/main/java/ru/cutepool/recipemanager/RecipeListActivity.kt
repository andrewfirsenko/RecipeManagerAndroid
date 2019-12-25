package ru.cutepool.recipemanager

import androidx.fragment.app.Fragment

class RecipeListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return RecipeListFragment()
    }
}