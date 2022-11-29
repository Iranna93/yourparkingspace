package com.reddit.test.domain.repositories

import com.reddit.test.domain.model.DomainPostModel
import com.reddit.test.domain.sealed.DomainSealedResponse

interface DomainRedditPostRepository {
    suspend fun getPosts(): DomainSealedResponse<List<DomainPostModel>>
}