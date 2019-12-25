package ru.cutepool.recipemanager.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recipe_single.*
import ru.cutepool.recipemanager.DownloadImage
import ru.cutepool.recipemanager.MainActivity
import ru.cutepool.recipemanager.R
import ru.cutepool.recipemanager.models.data.RecipeItem

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.SingleViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener
    var items: List<RecipeItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
//        Log.d("DEBUG", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_recipe_single, parent, false)
        return SingleViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
//        Log.d("DEBUG", "onBindViewHolder")
        holder.bind(items[position])
    }

    fun updateData(data: List<RecipeItem>) {
        items = data
        notifyDataSetChanged()
    }

    inner class SingleViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer {
        init {
            convertView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
//                val item = items[adapterPosition]
//                Toast.makeText(it.context, "click ${item.id}", Toast.LENGTH_SHORT).show()
            }
        }

        override val containerView: View?
            get() = itemView

        fun bind(item: RecipeItem) {
            DownloadImage(iv_recipe).execute("${MainActivity().hostIpAddress}image?src=${item.image}")
            tv_title_recipe.text = item.name
            val category = when(item.category) {
                "BREAKFAST" -> "завтрак"
                "LUNCH" -> "обед"
                "DINNER" -> "ужин"
                "DESSERT" -> "десерт"
                else -> "Не понятно"
            }
            tv_category_recipe.text = "Категория: $category"
        }
    }

    fun setListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}

interface ItemClickListener {
    fun onItemClick(position: Int)
}
