package com.example.myapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapp.activity.CategoryActivity
import com.example.myapp.adapter.CategoryAdapter
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.viewmodel.homeViewModel

class HomeFragment : Fragment() {

    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: homeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        observeViewModel()
        viewModel.fetchCategories(requireContext())
    }

    private fun setupAdapters() {
        categoryAdapter = CategoryAdapter(requireContext(), listOf()) { category ->
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            intent.putExtra("categoryId", category.id)
            intent.putExtra("categoryName", category.name)
            startActivity(intent)
        }
        binding.cateList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.cateList.adapter = categoryAdapter
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.updateCategories(categories)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
