package com.example.myapp.adapter

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
import com.example.myapp.repository.Category
import java.io.IOException

class CategoryAdapter(
    private val context: Context,
    private var categoryList: List<Category>,
    private val onItemClicked: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_category)
        val nameView: TextView = itemView.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        holder.nameView.text = category.name

            try {
                val inputStream = category.imageCategory?.let { context.assets.open(it) }
                val bitmap = BitmapFactory.decodeStream(inputStream)
                holder.imageView.setImageBitmap(bitmap)
                holder.imageView.setBackgroundColor(Color.TRANSPARENT)
            } catch (e: IOException) {
                e.printStackTrace()
                holder.imageView.setImageResource(0)
            }

        holder.itemView.setOnClickListener {
            onItemClicked(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun updateCategories(newCategories: List<Category>) {
        categoryList = newCategories
        notifyDataSetChanged()
    }
}
