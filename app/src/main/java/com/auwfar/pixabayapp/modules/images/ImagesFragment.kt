package com.auwfar.pixabayapp.modules.images

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auwfar.pixabayapp.R
import com.auwfar.pixabayapp.databinding.FragmentImagesBinding
import com.auwfar.pixabayapp.modules.images.config.ImagesInjection
import com.auwfar.pixabayapp.modules.search.SearchActivity
import com.auwfar.pixabayapp.network.NetworkListener

class ImagesFragment : Fragment(), NetworkListener {
    private lateinit var binding: FragmentImagesBinding
    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var viewModel: ImagesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        imagesAdapter = ImagesAdapter()

        setupViewModel()
        setupView()
    }

    private fun setupView() {
        binding.rvList.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = imagesAdapter
        }

        val spinnerItems = resources.getStringArray(R.array.category)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        binding.spinnerCategory.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val categoryCode = resources.getStringArray(R.array.categoryCode)[position]

                    showLoading(true)
                    viewModel.fetchData(categoryCode)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ImagesInjection.provideViewModelFactory()).get(ImagesViewModel::class.java)
        viewModel.networkListener = this

        viewModel.getImages().observe(this, Observer {
            val data = it ?: return@Observer

            imagesAdapter.setData(data)
            binding.rvList.scrollToPosition(0)

            if (data.isEmpty()) {
                binding.rvList.visibility = View.GONE
                binding.textLabelEmpty.visibility = View.VISIBLE
            } else {
                binding.rvList.visibility = View.VISIBLE
                binding.textLabelEmpty.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search_icon) {
            startActivity(Intent(context, SearchActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess() {
        showLoading(false)
    }

    override fun onFailure(msg: String?) {
        showLoading(false)
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
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
