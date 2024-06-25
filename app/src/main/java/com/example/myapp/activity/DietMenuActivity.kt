package com.example.myapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.adapter.DietListAdapter
import com.example.myapp.databinding.DietActivityBinding
import com.example.myapp.repository.Recipe
import com.example.myapp.viewmodel.DietMenuViewModel

class DietMenuActivity : ComponentActivity() {

    private lateinit var binding: DietActivityBinding
    private lateinit var mondayAdapter: DietListAdapter
    private lateinit var tuesdayAdapter: DietListAdapter
    private lateinit var wednesdayAdapter: DietListAdapter
    private lateinit var thursdayAdapter: DietListAdapter
    private lateinit var fridayAdapter: DietListAdapter
    private lateinit var saturdayAdapter: DietListAdapter
    private lateinit var sundayAdapter: DietListAdapter
    private val dietMenuViewModel: DietMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        super.onCreate(savedInstanceState)
        binding = DietActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val dietId = intent.getIntExtra("dietId", -1)
        if (dietId != -1) {
            setupRecyclerViews()
            dietMenuViewModel.fetchDietMenus(dietId)
            observeViewModel()
        }
    }

    private fun setupRecyclerViews() {
        mondayAdapter = DietListAdapter(this, listOf()) { recipe -> onRecipeClicked(recipe) }
        binding.mondayList.layoutManager = LinearLayoutManager(this)
        binding.mondayList.adapter = mondayAdapter

        tuesdayAdapter = DietListAdapter(this, listOf()) { recipe -> onRecipeClicked(recipe) }
        binding.tuesdayList.layoutManager = LinearLayoutManager(this)
        binding.tuesdayList.adapter = tuesdayAdapter

        wednesdayAdapter = DietListAdapter(this, listOf()) { recipe -> onRecipeClicked(recipe) }
        binding.wednesdayList.layoutManager = LinearLayoutManager(this)
        binding.wednesdayList.adapter = wednesdayAdapter

        thursdayAdapter = DietListAdapter(this, listOf()) { recipe -> onRecipeClicked(recipe) }
        binding.thursdayList.layoutManager = LinearLayoutManager(this)
        binding.thursdayList.adapter = thursdayAdapter

        fridayAdapter = DietListAdapter(this, listOf()) { recipe -> onRecipeClicked(recipe) }
        binding.fridayList.layoutManager = LinearLayoutManager(this)
        binding.fridayList.adapter = fridayAdapter

        saturdayAdapter = DietListAdapter(this, listOf()) { recipe -> onRecipeClicked(recipe) }
        binding.saturdayList.layoutManager = LinearLayoutManager(this)
        binding.saturdayList.adapter = saturdayAdapter

        sundayAdapter = DietListAdapter(this, listOf()) { recipe -> onRecipeClicked(recipe) }
        binding.sundayList.layoutManager = LinearLayoutManager(this)
        binding.sundayList.adapter = sundayAdapter
    }

    private fun observeViewModel() {
        dietMenuViewModel.dietName.observe(this, Observer { dietName ->
            binding.dietName.text = dietName
        })

        dietMenuViewModel.mondayRecipes.observe(this, Observer { recipes ->
            mondayAdapter.updateRecipes(recipes)
        })

        dietMenuViewModel.tuesdayRecipes.observe(this, Observer { recipes ->
            tuesdayAdapter.updateRecipes(recipes)
        })

        dietMenuViewModel.wednesdayRecipes.observe(this, Observer { recipes ->
            wednesdayAdapter.updateRecipes(recipes)
        })

        dietMenuViewModel.thursdayRecipes.observe(this, Observer { recipes ->
            thursdayAdapter.updateRecipes(recipes)
        })

        dietMenuViewModel.fridayRecipes.observe(this, Observer { recipes ->
            fridayAdapter.updateRecipes(recipes)
        })

        dietMenuViewModel.saturdayRecipes.observe(this, Observer { recipes ->
            saturdayAdapter.updateRecipes(recipes)
        })

        dietMenuViewModel.sundayRecipes.observe(this, Observer { recipes ->
            sundayAdapter.updateRecipes(recipes)
        })
    }

    private fun onRecipeClicked(recipe: Recipe) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("recipeId", recipe.id)
        startActivity(intent)
    }
}
