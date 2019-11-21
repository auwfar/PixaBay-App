package com.auwfar.pixabayapp.modules.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auwfar.pixabayapp.R
import com.auwfar.pixabayapp.databinding.FragmentFavoritesBinding
import com.auwfar.pixabayapp.modules.favorites.config.FavoritesInjection
import com.auwfar.pixabayapp.modules.images.ImageModel
import com.auwfar.pixabayapp.modules.images.ImagesAdapter

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        imagesAdapter = ImagesAdapter()

        binding.rvList.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = imagesAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, FavoritesInjection.provideViewModelFactory(activity!!.application)).get(FavoritesViewModel::class.java)

        viewModel.listImages.observe(this, Observer {
            val data = it ?: return@Observer

            val model = arrayListOf<ImageModel>()
            data.forEach { favoriteModel ->
                model.add(
                    ImageModel(
                        favoriteModel.id,
                        favoriteModel.tags,
                        favoriteModel.previewURL,
                        favoriteModel.imageURL,
                        favoriteModel.imageWidth,
                        favoriteModel.imageHeight,
                        favoriteModel.user,
                        favoriteModel.userImageURL
                    )
                )
            }
            imagesAdapter.setData(model)
            binding.progressBar.visibility = View.GONE

            if (data.isEmpty()) {
                binding.rvList.visibility = View.GONE
                binding.textLabelEmpty.visibility = View.VISIBLE
            } else {
                binding.rvList.visibility = View.VISIBLE
                binding.textLabelEmpty.visibility = View.GONE
            }
        })
    }
}
