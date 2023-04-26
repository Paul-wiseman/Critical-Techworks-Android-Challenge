package com.example.criticaltechworkschallenge.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criticaltechworkschallenge.domain.usecase.FetchTopHeadlineUseCase
import com.example.criticaltechworkschallenge.entity.ArticlePresentationModel
import com.example.criticaltechworkschallenge.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineNewsViewModel @Inject constructor(
    private val fetchTopHeadlineUseCase: FetchTopHeadlineUseCase
) : ViewModel() {

    private var _headLinePostResponse = MutableLiveData<Resource<List<ArticlePresentationModel>>>()
    val headLinePostResponse get() = _headLinePostResponse

    fun fetchTopHeadlinePosts() {
        headLinePostResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            fetchTopHeadlineUseCase.invoke()
                .collectLatest { value: Resource<List<ArticlePresentationModel>> ->
                    headLinePostResponse.postValue(value)
                }
        }
    }
}