package com.android.topheadlinesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.topheadlinesapp.R
import com.android.topheadlinesapp.databinding.ItemTopHeadlinesBinding
import com.android.topheadlinesapp.interfaces.ItemClickListener
import com.bumptech.glide.Glide
import com.tha.core.models.topHeadlines.Article

class TopHeadlinesAdapter(var data: List<Article>, private val itemEventsListener: ItemClickListener): RecyclerView.Adapter<TopHeadlinesAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemTopHeadlinesBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    private lateinit var binding: ItemTopHeadlinesBinding

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemTopHeadlinesBinding.inflate(layoutInflater, parent,false)
        context = parent.context
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = data[position]
        holder.binding.article = data

        if(data.urlToImage.isNotEmpty()) {
            Glide.with(context).load(data.urlToImage).placeholder(R.drawable.placeholder).into(holder.binding.imageView)
        }

        holder.binding.root.setOnClickListener {
            itemEventsListener.onItemClickListener(position, data)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}