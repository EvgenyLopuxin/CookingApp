package com.example.myapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapp.R
import com.example.myapp.adapter.CategoryListAdapter
import com.example.myapp.databinding.CategoryActivityBinding
import com.example.myapp.viewmodel.CategoryViewModel

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: CategoryActivityBinding
    private lateinit var categoryListAdapter: CategoryListAdapter
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        super.onCreate(savedInstanceState)
        binding = CategoryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val categoryName = intent.getStringExtra("categoryName") ?: return
        val categoryId = intent.getIntExtra("categoryId", -1)
        if (categoryId == -1) {
            finish()
            return
        }

        binding.categoryNameActivity.text = categoryName

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchRecipesByCategoryId(categoryId, applicationContext)
    }

    private fun setupRecyclerView() {
        categoryListAdapter = CategoryListAdapter(this) { recipe ->
            val intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("recipeId", recipe.id)
            startActivity(intent)
        }
        binding.cateListActivity.layoutManager = GridLayoutManager(this, 2)
        binding.cateListActivity.adapter = categoryListAdapter
    }

    private fun observeViewModel() {
        viewModel.recipes.observe(this, Observer { recipes ->
            categoryListAdapter.updateRecipes(recipes)
        })
    }
}
