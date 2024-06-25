package com.example.myapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.activity.ItemActivity
import com.example.myapp.adapter.RecipeAdapter
import com.example.myapp.databinding.FragmentFavoriteBinding
import com.example.myapp.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recipeAdapter: RecipeAdapter
    private val viewModel: FavoriteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchFavoriteRecipes()
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(requireContext(), emptyList()) { recipe ->
            val intent = Intent(requireContext(), ItemActivity::class.java)
            intent.putExtra("recipeId", recipe.id)
            startActivity(intent)
        }

        binding.favList.layoutManager = LinearLayoutManager(requireContext())
        binding.favList.adapter = recipeAdapter
    }

    private fun observeViewModel() {
        viewModel.favoriteRecipes.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes.isEmpty()) {
                binding.favText.visibility = View.VISIBLE
                binding.favList.visibility = View.GONE
            } else {
                binding.favText.visibility = View.VISIBLE
                binding.favList.visibility = View.VISIBLE
                recipeAdapter.updateRecipes(recipes)
            }
        })
    }
}
