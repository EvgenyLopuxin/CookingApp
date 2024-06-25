package com.example.myapp.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.databinding.CategoryItemBinding
import com.example.myapp.repository.Recipe
import java.io.IOException

class CategoryListAdapter(
    private val context: Context,
    private val onItemClicked: (Recipe) -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private var itemList: List<Recipe> = listOf()

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imgCateItem
        val nameView: TextView = binding.textCateItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = itemList[position]

        holder.nameView.text = item.title


            try {
                val inputStream = item.imageRecipe?.let { context.assets.open(it) }
                val bitmap = BitmapFactory.decodeStream(inputStream)
                holder.imageView.setImageBitmap(bitmap)
                holder.imageView.setBackgroundColor(Color.TRANSPARENT)
            } catch (e: IOException) {
                e.printStackTrace()
                holder.imageView.setImageResource(R.drawable.placeholder_image)
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
