package com.reddit.test.presentation.posts.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reddit.test.domain.model.DomainPostModel
import com.reddit.test.domain.sealed.DomainSealedResponse
import com.reddit.test.presentation.posts.compose.PostActionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsModel: PostsModel
) : ViewModel(), PostActionListener {

    sealed class PostsIntention {
        object RequestPostsList : PostsIntention()
        data class RequestPostDetailsPage(val url: String) : PostsIntention()
    }

    sealed class CurrentUIState {
        object NotLoaded : CurrentUIState()
        object Loading : CurrentUIState()
        data class ShowPostsList(val data: List<DomainPostModel>) : CurrentUIState()
        data class Error(val errorCode: Int?, val errorMessages: String?) : CurrentUIState()
    }

    sealed class SideEffects {
        data class NavigateToPostDetails(val url: String) : SideEffects()
    }

    // StateFlows
    private val _postsStateFlow = MutableStateFlow<CurrentUIState>(
        CurrentUIState.NotLoaded
    )
    val postStateFlow: StateFlow<CurrentUIState> = _postsStateFlow

    private val _sideEffects: Channel<SideEffects.NavigateToPostDetails> = Channel()
    val sideEffects = _sideEffects.receiveAsFlow()

    private fun triggerPostList() {
        viewModelScope.launch() {
            _postsStateFlow.emit(CurrentUIState.Loading)
            when (val response = postsModel.getPosts()) {
                is DomainSealedResponse.Success -> {
                    if (!response.data.isNullOrEmpty()) {
                        _postsStateFlow.emit(CurrentUIState.ShowPostsList(data = response.data))
                    } else {
                        _postsStateFlow.emit(CurrentUIState.NotLoaded)
                    }
                }
                is DomainSealedResponse.Error -> {
                    _postsStateFlow.emit(
                        CurrentUIState.Error(
                            response.error?.errorCode,
                            response.error?.errorMessage
                        )
                    )
                }
            }
        }
    }

    private fun triggerPostDetailsDialog(url: String) {
        viewModelScope.launch {
            _sideEffects.send(SideEffects.NavigateToPostDetails(url = url))
        }
    }

    override fun acceptNewIntention(intention: PostsIntention) {
        when (intention) {
            is PostsIntention.RequestPostsList -> {
                triggerPostList()
            }
            is PostsIntention.RequestPostDetailsPage -> {
                triggerPostDetailsDialog(url = intention.url)
            }
        }
    }
}