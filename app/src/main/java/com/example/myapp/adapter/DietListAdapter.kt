package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.repository.Recipe
import java.io.IOException

class DietListAdapter(
    private val context: Context,
    private var itemList: List<Recipe>,
    private val onItemClicked: (Recipe) -> Unit
) : RecyclerView.Adapter<DietListAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_rec_item)
        val titleView: TextView = itemView.findViewById(R.id.text_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_items, parent, false)
        return RecipeViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = itemList[position]

        holder.titleView.text = item.title


            try {
                val inputStream = item.imageRecipe?.let { context.assets.open(it) }
                val bitmap = BitmapFactory.decodeStream(inputStream)
                holder.imageView.setImageBitmap(bitmap)
                holder.imageView.setBackgroundColor(Color.TRANSPARENT)
            } catch (e: IOException) {
                e.printStackTrace()
                holder.imageView.setImageResource(0)
                holder.imageView.setBackgroundColor(Color.RED)
            }


        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateRecipes(newItems: List<Recipe>) {
        itemList = newItems
        notifyDataSetChanged()
    }
}
