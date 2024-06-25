package com.example.myapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.activity.DietMenuActivity
import com.example.myapp.adapter.DietAdapter
import com.example.myapp.databinding.FragmentDietBinding
import com.example.myapp.viewmodel.DietViewModel

class DietFragment : Fragment() {

    private lateinit var dietAdapter: DietAdapter
    private lateinit var binding: FragmentDietBinding
    private val dietViewModel: DietViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDietBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        dietViewModel.diets.observe(viewLifecycleOwner, Observer { diets ->
            dietAdapter.updateDiets(diets)
        })
    }

    private fun setupRecyclerView() {
        dietAdapter = DietAdapter(requireContext(), listOf()) { diet ->
            val intent = Intent(requireContext(), DietMenuActivity::class.java)
            intent.putExtra("dietId", diet.id)
            startActivity(intent)
        }
        binding.dietFragmentList.layoutManager = LinearLayoutManager(requireContext())
        binding.dietFragmentList.adapter = dietAdapter
    }
}
