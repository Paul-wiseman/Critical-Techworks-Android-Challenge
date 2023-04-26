package com.example.criticaltechworkschallenge.presentation.ui.fragment.headline.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.criticaltechworkschallenge.databinding.TopHeadlineNewItemLayoutBinding
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.presentation.ui.fragment.headline.TopHeadlineFragment
import com.example.criticaltechworkschallenge.presentation.ui.fragment.headline.TopHeadlineFragmentDirections
import com.example.criticaltechworkschallenge.util.NewsDiffUtil

class TopHeadlineNewsAdapter :
    RecyclerView.Adapter<TopHeadlineViewHolder>() {

    private var headlinePosts = emptyList<ArticlePresentationModel>()

    override fun onBindViewHolder(holder: TopHeadlineViewHolder, position: Int) {
        val currentPost = headlinePosts[position]
        holder.bind(currentPost, createOnClickListener(currentPost))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlineViewHolder {
        return TopHeadlineViewHolder(
            TopHeadlineNewItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun getItemCount() = headlinePosts.size

    fun submitList(newPosts: List<ArticlePresentationModel>) {
        val sortedPost = newPosts.sortedBy { it.publishedAt }
        val headlinePostsDiffUtil = NewsDiffUtil(headlinePosts, sortedPost)
        val diffUtilResult = DiffUtil.calculateDiff(headlinePostsDiffUtil)
        headlinePosts = sortedPost
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun createOnClickListener(
        item: ArticlePresentationModel
    ): View.OnClickListener {
        return View.OnClickListener {
            val directions =
                TopHeadlineFragmentDirections.actionHeadlineFragmentToNewsDetailFragment(item)
            navigate(directions, it.findNavController())
        }
    }

    private fun navigate(directions: NavDirections, controller: NavController) {
        val currentDestination =
            (controller.currentDestination as? FragmentNavigator.Destination)?.className
                ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
        if (currentDestination == TopHeadlineFragment().javaClass.name) {
            controller.navigate(directions)
        }
    }
}


