package com.example.companyapp

import CompanyAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.companyapp.databinding.ActivityMainBinding
import com.example.companyapp.viewmodel.CompanyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<CompanyViewModel>()

    private lateinit var adapter: CompanyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        adapter = CompanyAdapter(viewModel)
        binding.companyRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.companyRecyclerView.layoutManager = layoutManager

        viewModel.companies.observe(this) {
            binding.companyRecyclerView.post {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupButtons() {
        binding.addMoreButton.setOnClickListener {
            viewModel.addCompany()
        }

        binding.submitButton.setOnClickListener {
            if (viewModel.submitCompanies()) {
                Toast.makeText(this, "Companies: ${viewModel.companies.value}", Toast.LENGTH_LONG)
                    .show()
                Log.d("CompanyFragment", "Companies: ${viewModel.companies.value}")
            } else {
                Toast.makeText(this, "Validation failed", Toast.LENGTH_LONG).show()
                Log.e("CompanyFragment", "Validation failed")
            }
        }
    }
}