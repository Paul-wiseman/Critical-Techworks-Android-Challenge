package com.example.criticaltechworkschallenge.presentation.ui.fragment.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.criticaltechworkschallenge.R
import com.example.criticaltechworkschallenge.databinding.FragmentNewsDetailBinding
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel


class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NewsDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent(args.article)
    }

    private fun initComponent(article: ArticlePresentationModel) {
        with(binding) {
            source.text = article.source?.name
            title.text = article.title
            authors.text =
                String.format(getString(R.string.author), article.author ?: article.source?.name)
            dateTime.text = article.publishedAt
            tvDescription.text = article.description
            tvContent.text = article.content
            headlineImage.load(article.urlToImage) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }
}