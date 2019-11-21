package com.auwfar.pixabayapp.modules.images.detail

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.auwfar.pixabayapp.R
import com.auwfar.pixabayapp.databinding.ActivityImagesDetailBinding
import com.auwfar.pixabayapp.modules.favorites.FavoritesViewModel
import com.auwfar.pixabayapp.modules.favorites.config.FavoritesEntity
import com.auwfar.pixabayapp.modules.favorites.config.FavoritesInjection
import com.auwfar.pixabayapp.modules.images.ImageModel
import com.bumptech.glide.Glide

class ImagesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagesDetailBinding
    private lateinit var favoriteViewModel: FavoritesViewModel
    private lateinit var menuItem: Menu

    private var image: ImageModel? = null
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_MOVIE = "extra_image"

        @JvmStatic
        fun newIntent(packageContext: Context, data: ImageModel): Intent {
            return Intent(packageContext, ImagesDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, data)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_images_detail)
        favoriteViewModel = ViewModelProvider(this, FavoritesInjection.provideViewModelFactory(application)).get(FavoritesViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        image = intent.getParcelableExtra(EXTRA_MOVIE)
        if (image != null) {
            setupView()
        }
    }

    private fun setupView() {
        val image = image ?: return
        title = "Image Detail"

        Glide.with(this)
            .load(image.imageURL)
            .into(binding.imgPhoto)

        Glide.with(this)
            .load(image.userImageURL)
            .into(binding.imgUser)

        binding.textUserName.text = image.user
        binding.textImageSize.text = "${image.imageWidth} X ${image.imageHeight}"
        binding.textTags.text = image.tags
    }

    private fun setupViewModel() {
        favoriteViewModel.listImages.observe(this, Observer {images ->
            if (images.find{it.id == image!!.id} != null) {
                menuItem.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
                isFavorite = true
            } else {
                menuItem.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
                isFavorite = false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        menuItem = menu
        setupViewModel()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun removeFromFavorite() {
        image?.let { image ->
            favoriteViewModel.deleteImages(image.id)
        }
    }

    private fun addToFavorite() {
        image?.let { image ->
            favoriteViewModel.insertImages(
                FavoritesEntity(
                    image.id,
                    image.tags,
                    image.previewURL,
                    image.imageURL,
                    image.imageWidth,
                    image.imageHeight,
                    image.user,
                    image.userImageURL
                )
            )
        }
    }
}
