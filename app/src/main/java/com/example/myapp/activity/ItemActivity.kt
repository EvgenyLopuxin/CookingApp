package com.example.myapp.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.appcompat.widget.Toolbar
import com.example.myapp.R
import com.example.myapp.databinding.ItemActivityBinding
import com.example.myapp.repository.IngredientWithQuantity
import com.example.myapp.repository.Recipe
import com.example.myapp.viewmodel.ItemViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import androidx.core.content.ContextCompat.getColor
import java.io.IOException

class ItemActivity : AppCompatActivity() {
    private lateinit var binding: ItemActivityBinding
    private val viewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        binding = ItemActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        val recipeId = intent.getIntExtra("recipeId", -1)
        if (recipeId != -1) {
            viewModel.fetchRecipeDetails(recipeId, applicationContext)
        } else {
            Toast.makeText(this, "Error loading recipe", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.favBtn.setOnClickListener {
            viewModel.toggleFavoriteStatus(applicationContext)
        }

        observeViewModel()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = binding.toolbar
        val toolbarTitle: TextView = binding.toolbarTitle

        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbarTitle.text = ""
        toolbarTitle.startAlphaAnimation(false, 0)

        val appBarLayout: AppBarLayout = binding.appBarLayout
        var isTitleVisible = false
        appBarLayout.addOnOffsetChangedListener { appBar, offset ->
            val isVisible = abs(offset.toFloat()) / appBar.totalScrollRange >= 0.9f
            if (isVisible != isTitleVisible) {
                toolbarTitle.startAlphaAnimation(
                    isVisible,
                    appBar.resources.getInteger(com.google.android.material.R.integer.app_bar_elevation_anim_duration)
                        .toLong()
                )
                toolbar.setBackgroundColor(
                    if (isVisible) getColor(this, R.color.orange)
                    else getColor(this, android.R.color.transparent)
                )
                isTitleVisible = isVisible
            }
        }
    }

    private fun observeViewModel() {
        viewModel.recipe.observe(this, Observer { recipe ->
            recipe?.let {
                displayRecipeDetails(it)
                updateFavoriteButton(it.isFav == 1)
            }
        })

        viewModel.ingredients.observe(this, Observer { ingredients ->
            displayIngredients(ingredients)
        })

        viewModel.errorMessage.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearErrorMessage()
            }
        })
    }

    private fun displayRecipeDetails(recipe: Recipe) {
        binding.collapsing.title = recipe.title
        binding.toolbarTitle.text = recipe.title
        binding.titleItem.text = recipe.title

        binding.textBel.text = formatQuantity(recipe.protein)
        binding.textJir.text = formatQuantity(recipe.fat)
        binding.textUgl.text = formatQuantity(recipe.carbohydrates)
        binding.textCal.text = formatQuantity(recipe.calories)

        if (recipe.imageRecipe != null) {
            val assetManager = assets
            try {
                val inputStream = assetManager.open(recipe.imageRecipe)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                binding.imgItem.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        binding.dirText.text = recipe.instructions
    }

    private fun displayIngredients(ingredients: List<IngredientWithQuantity>) {
        val ingredientsText = ingredients.joinToString("\n") {
            "${it.name} ${formatQuantity(it.quantity)} ${it.unit}"
        }
        binding.ingText.text = ingredientsText
    }

    private fun formatQuantity(quantity: Double?): String {
        return when {
            quantity == null || quantity == 0.0 -> ""
            quantity % 1.0 == 0.0 -> quantity.toInt().toString()
            else -> quantity.toString()
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val favIconRes = if (isFavorite) {
            R.drawable.ic_fav_true
        } else {
            R.drawable.ic_favorite
        }
        binding.favBtn.setImageResource(favIconRes)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

fun View.startAlphaAnimation(isVisible: Boolean, duration: Long) {
    val alpha = if (isVisible) 1f else 0f
    this.animate().alpha(alpha).setDuration(duration).start()
}
