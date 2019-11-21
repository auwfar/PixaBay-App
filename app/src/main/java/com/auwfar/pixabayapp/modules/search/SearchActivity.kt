package com.auwfar.pixabayapp.modules.search

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auwfar.pixabayapp.R
import com.auwfar.pixabayapp.databinding.ActivitySearchBinding
import com.auwfar.pixabayapp.modules.images.ImagesAdapter
import com.auwfar.pixabayapp.modules.images.ImagesViewModel
import com.auwfar.pixabayapp.modules.images.config.ImagesInjection
import com.auwfar.pixabayapp.network.NetworkListener
import com.miguelcatalan.materialsearchview.MaterialSearchView

class SearchActivity : AppCompatActivity(), NetworkListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: ImagesViewModel
    private lateinit var newsAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search"

        setupView()
        setupViewModel()
        setupSearchListener()
    }

    private fun setupView() {
        newsAdapter = ImagesAdapter()

        binding.rvList.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = newsAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ImagesInjection.provideViewModelFactory()).get(ImagesViewModel::class.java)
        viewModel.networkListener = this

        viewModel.getSearchResult().observe(this, Observer {
            val data = it ?: return@Observer

            newsAdapter.setData(data)

            if (data.isEmpty()) {
                binding.rvList.visibility = View.GONE
                binding.textLabelEmpty.visibility = View.VISIBLE
            } else {
                binding.rvList.visibility = View.VISIBLE
                binding.textLabelEmpty.visibility = View.GONE
            }
        })
    }

    private fun setupSearchListener() {
        binding.searchView.setHint("Search ...")

        binding.searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) return false

                showLoading(true)
                viewModel.searchData(query)
                supportActionBar?.title = query
                return false
            }

            override fun onQueryTextChange(searchText: String): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val item = menu?.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)

        binding.searchView.showSearch()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.searchView.isSearchOpen) {
            binding.searchView.closeSearch()
            return
        }

        super.onBackPressed()
    }

    override fun onSuccess() {
        showLoading(false)
    }

    override fun onFailure(msg: String?) {
        showLoading(false)
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state: Boolean) {
        binding.textLabelEmpty.visibility = View.GONE

        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
