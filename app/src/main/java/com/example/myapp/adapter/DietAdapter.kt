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
import com.example.myapp.adapter.DietAdapter.DietViewHolder
import com.example.myapp.repository.Diet
import java.io.IOException

class DietAdapter(
    private val context: Context,
    private var dietList: List<Diet>,
    private val onItemClicked: (Diet) -> Unit
) : RecyclerView.Adapter<DietViewHolder>() {

    inner class DietViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_diet_card)
        val titleView: TextView = itemView.findViewById(R.id.text_diet_card)
        val authorView: TextView = itemView.findViewById(R.id.name_diet_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diet_card, parent, false)
        return DietViewHolder(view)
    }

    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        val diet = dietList[position]

        holder.titleView.text = diet.name
        holder.authorView.text = diet.authorName


            try {
                val inputStream = diet.imageAuthor?.let { context.assets.open(it) }
                val bitmap = BitmapFactory.decodeStream(inputStream)
                holder.imageView.setImageBitmap(bitmap)
                holder.imageView.setBackgroundColor(Color.TRANSPARENT)
            } catch (e: IOException) {
                e.printStackTrace()
                holder.imageView.setImageResource(0)
                holder.imageView.setBackgroundColor(Color.RED)
            }

        holder.itemView.setOnClickListener {
            onItemClicked(diet)
        }
    }

    override fun getItemCount(): Int {
        return dietList.size
    }

    fun updateDiets(newDiets: List<Diet>) {
        dietList = newDiets
        notifyDataSetChanged()
    }
}
