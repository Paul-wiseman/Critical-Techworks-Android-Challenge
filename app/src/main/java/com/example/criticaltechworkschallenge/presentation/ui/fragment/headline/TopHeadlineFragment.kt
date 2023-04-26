package com.example.criticaltechworkschallenge.presentation.ui.fragment.headline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.criticaltechworkschallenge.R
import com.example.criticaltechworkschallenge.databinding.FragmentHeadlineBinding
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.presentation.ui.fragment.headline.adapter.TopHeadlineNewsAdapter
import com.example.criticaltechworkschallenge.presentation.viewmodel.TopHeadlineNewsViewModel
import com.example.criticaltechworkschallenge.util.Constants.IS_AUTHENTICATION_SUCCESSFUL
import com.example.criticaltechworkschallenge.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopHeadlineFragment : Fragment(R.layout.fragment_headline) {
    private var _binding: FragmentHeadlineBinding? = null
    private val binding get() = _binding!!
    private val topHeadlineNewsAdapter: TopHeadlineNewsAdapter by lazy { TopHeadlineNewsAdapter() }
    private val viewModel: TopHeadlineNewsViewModel by viewModels()
    private var isBiometricAuthenticationSuccessful = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isBiometricAuthenticationSuccessful = it.getBoolean(IS_AUTHENTICATION_SUCCESSFUL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHeadlineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerview()
        initObserver()

    }

    override fun onResume() {
        super.onResume()
        if (isBiometricAuthenticationSuccessful) fetchTopHeadlinePost()
    }

    private fun fetchTopHeadlinePost() {
        viewModel.fetchTopHeadlinePosts()
        com.example.criticaltechworkschallenge.BuildConfig.sources
    }


    private fun initObserver() {
        viewModel.headLinePostResponse.observe(viewLifecycleOwner) { value: Resource<List<ArticlePresentationModel>> ->
            binding.textViewError.isVisible = value is Resource.Error && value.data.isNullOrEmpty()
            binding.textViewError.text = value.error?.localizedMessage
            when (value) {
                is Resource.Error -> {
                    hideLoading()
                    value.data?.let { topHeadlineNewsAdapter.submitList(it) }
                }
                is Resource.Loading -> showLoading()
                is Resource.Success -> {
                    hideLoading()
                    value.data?.let { topHeadlineNewsAdapter.submitList(it) }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun initRecyclerview() {
        with(binding) {
            rvHeadline.adapter = topHeadlineNewsAdapter
            rvHeadline.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}