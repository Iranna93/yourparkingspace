package com.reddit.test.domain.usecase.posts

import com.reddit.test.domain.model.DomainPostModel
import com.reddit.test.domain.repositories.DomainRedditPostRepository
import com.reddit.test.domain.sealed.DomainSealedResponse
import com.reddit.test.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val domainRedditPostRepository: DomainRedditPostRepository
) : BaseUseCase<DomainSealedResponse<List<DomainPostModel>>>() {
    override suspend fun execute(): DomainSealedResponse<List<DomainPostModel>> {
        return domainRedditPostRepository.getPosts()
    }
}