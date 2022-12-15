package com.example.checkacronyms

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkacronyms.databinding.ActivityMainBinding
import com.example.checkacronyms.db.ApiHelper
import com.example.checkacronyms.db.MainRepository
import com.example.checkacronyms.network.ApiClient
import com.example.checkacronyms.network.ApiService
import com.example.checkacronyms.utils.AcronymViewModelFactory
import com.google.android.material.internal.ViewUtils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AcronymViewModel
    private lateinit var longFormAdapter: LongFormAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupViewModel()

        setupAdapter()

        setupObserver()

        binding.button.setOnClickListener {
            hideKeyboard(it)
            if (!TextUtils.isEmpty(binding.editTextView.text)) {
                viewModel.getDefinitions(binding.editTextView.text.toString())
            } else {
                binding.editTextView.error = resources.getString(R.string.err_msg)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupObserver() {
        viewModel.longFormList.observe(this) {
            longFormAdapter.setData(it)
        }
    }

    private fun setupAdapter() {
        longFormAdapter = LongFormAdapter()
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        binding.recyclerView.apply {
            adapter = longFormAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun setupViewModel() {
        val retrofit = ApiClient.getApiClient()
        val apiService = retrofit.create(ApiService::class.java)
        val apiHelper = ApiHelper(apiService)
        val mainRepository = MainRepository(apiHelper)

        viewModel = ViewModelProvider(this, AcronymViewModelFactory(mainRepository)).get(AcronymViewModel::class.java)
        binding.viewModel = this.viewModel
    }
}