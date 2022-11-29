package com.reddit.test.data.repositories

import com.reddit.test.data.Constants
import com.reddit.test.data.boundary.DomainMapper
import com.reddit.test.data.source.local.PostsDao
import com.reddit.test.data.source.remote.RetrofitService
import com.reddit.test.domain.model.DomainPostModel
import com.reddit.test.domain.repositories.DomainRedditPostRepository
import com.reddit.test.domain.sealed.DomainErrorResponse
import com.reddit.test.domain.sealed.DomainSealedResponse

class RedditPostRepositoryImpl(
    private val retrofitService: RetrofitService,
    private val domainMapper: DomainMapper,
) : DomainRedditPostRepository {
    override suspend fun getPosts(): DomainSealedResponse<List<DomainPostModel>> {

        val posts =
            domainMapper.mapPostResponseToDomainModel(retrofitService.getPosts(Constants.POSTS_DATA_SOURCE))

        return if (posts.isNullOrEmpty()) {
            DomainSealedResponse.Error(DomainErrorResponse(12, "Not available"))
        } else {
            DomainSealedResponse.Success(data = posts)
        }
    }
}