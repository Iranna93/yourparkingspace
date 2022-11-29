package com.reddit.test.presentation.posts.mvvm

import com.reddit.test.domain.model.DomainPostModel
import com.reddit.test.domain.sealed.DomainSealedResponse
import com.reddit.test.domain.usecase.posts.GetPostsUseCase
import javax.inject.Inject

class PostsModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) {
    suspend fun getPosts(): DomainSealedResponse<List<DomainPostModel>> {
        return getPostsUseCase.execute()
    }
}