package com.reddit.test.data.boundary

import com.reddit.test.data.source.remote.model.PostResponse
import com.reddit.test.domain.model.DomainPostModel
import javax.inject.Inject

class DomainMapper @Inject constructor() {
    fun mapPostResponseToDomainModel(postResponse: PostResponse): List<DomainPostModel> {
        //TODO : Current taking only required parameters to display
        return postResponse.data.children.map {
            DomainPostModel(
                title = it.data.title,
                created = it.data.created,
                selftext = it.data.selftext,
                author = it.data.author,
                selftextHtml = it.data.selftextHtml,
                subreddit = it.data.subreddit,
                url = it.data.url,
                thumbnail = it.data.thumbnail,
                downs = it.data.downs,
                isVideo = it.data.isVideo,
                numComments = it.data.numComments,
                saved = it.data.saved,
                ups = it.data.ups,
                thumbnailWidth = it.data.thumbnailWidth,
                thumbnailHeight = it.data.thumbnailHeight
            )
        }
    }
}