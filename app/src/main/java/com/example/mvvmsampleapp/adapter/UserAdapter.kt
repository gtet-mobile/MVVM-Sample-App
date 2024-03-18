package com.example.mvvmsampleapp.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.mvvmsampleapp.databinding.UserItemBinding
import com.example.mvvmsampleapp.model.Data
import com.example.mvvmsampleapp.utils.ViewUtils
import com.example.mvvmsampleapp.utils.hide


class UserAdapter(
    val onItemClicked: (Data) -> Unit,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data: List<Data>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }


    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("CheckResult")
        fun bind(data: Data) {
            binding.item = data

            binding.root.setOnClickListener {
                onItemClicked(list[adapterPosition])
            }
            val requestOptions = RequestOptions()
            requestOptions.placeholder(ViewUtils.randomDrawbleColor)
            requestOptions.error(ViewUtils.randomDrawbleColor)
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
            requestOptions.centerCrop()

            Glide.with(binding.image.context)
                .load(data.avatar)
                .apply(requestOptions)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loader.hide()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable?>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loader.hide()
                        return false
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.image)
        }
    }
}