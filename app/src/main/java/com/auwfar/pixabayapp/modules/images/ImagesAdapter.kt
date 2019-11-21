package com.auwfar.pixabayapp.modules.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.auwfar.pixabayapp.R
import com.auwfar.pixabayapp.databinding.ItemImagesBinding
import com.auwfar.pixabayapp.modules.images.detail.ImagesDetailActivity
import com.bumptech.glide.Glide

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    private var data = listOf<ImageModel>()

    fun setData(items: List<ImageModel>) {
        data = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_images,
                    parent,
                    false
                )
            )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    class ViewHolder(private val binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: ImageModel) {
            binding.textUser.text = data.user

            itemView.run {
                Glide.with(context)
                    .load(data.previewURL)
                    .into(binding.imgPhoto)

                Glide.with(context)
                    .load(data.userImageURL)
                    .into(binding.imgUser)
            }

            itemView.run {
                setOnClickListener {
                    context.startActivity(ImagesDetailActivity.newIntent(itemView.context, data))
                }
            }
        }
    }
}