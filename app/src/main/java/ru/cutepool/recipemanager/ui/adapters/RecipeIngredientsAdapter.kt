package ru.cutepool.recipemanager.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recipe_ingredient.*
import ru.cutepool.recipemanager.R
import ru.cutepool.recipemanager.models.data.RecipeIngredient

class RecipeIngredientsAdapter : RecyclerView.Adapter<RecipeIngredientsAdapter.SingleViewHolder>() {
    var items: List<RecipeIngredient> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_recipe_ingredient, parent, false)
        return SingleViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(data: List<RecipeIngredient>) {
        items = data
        notifyDataSetChanged()
    }

    inner class SingleViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bind(item: RecipeIngredient) {
            tv_ingredient.text = "${item.name} ${item.quantity}"
        }
    }
}
