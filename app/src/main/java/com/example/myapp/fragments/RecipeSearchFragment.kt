package com.example.myapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.example.myapp.activity.ItemActivity
import com.example.myapp.adapter.RecipeAdapter
import com.example.myapp.databinding.FragmentRecipeSearchBinding
import com.example.myapp.viewmodel.RecipeSearchViewModel

class RecipeSearchFragment : Fragment() {

    private lateinit var adapter: RecipeAdapter
    private var _binding: FragmentRecipeSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipeSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipeAdapter(requireContext(), listOf()) { recipe ->
            navigateToItemActivity(recipe.id)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        setupSearchView()
        observeViewModel()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().trim()
                viewModel.searchRecipes(query, requireContext())
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            adapter.updateRecipes(recipes)
        })
    }

    private fun navigateToItemActivity(recipeId: Int) {
        val intent = Intent(requireContext(), ItemActivity::class.java)
        intent.putExtra("recipeId", recipeId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
