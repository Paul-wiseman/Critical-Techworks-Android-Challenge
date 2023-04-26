package com.example.criticaltechworkschallenge.presentation.ui.fragment.headline.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.criticaltechworkschallenge.R
import com.example.criticaltechworkschallenge.databinding.TopHeadlineNewItemLayoutBinding
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel

class TopHeadlineViewHolder(val binding: TopHeadlineNewItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(article: ArticlePresentationModel, listener: View.OnClickListener) {
        with(binding) {
            title.text = article.source?.name
            tvContentTitle.text = article.title
            postedBy.text = article.author ?: article.source?.name
            date.text = article.publishedAt
            postImage.load(article.urlToImage) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }

            root.setOnClickListener(listener)
        }


    }
}