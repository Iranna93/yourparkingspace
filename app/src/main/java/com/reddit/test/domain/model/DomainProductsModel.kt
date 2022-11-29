package com.reddit.test.domain.model

data class DomainPostModel(
    val subreddit: String,
    val selftext: String,
    val title: String,
    val author: String,
    val selftextHtml: String?,
    val created: Int,
    val url: String,
    val thumbnail: String?,
    val isVideo: Boolean,
    val ups: Int,
    val downs: Int,
    val numComments: Int,
    val saved: Boolean,
    val thumbnailWidth: Int,
    val thumbnailHeight: Int
)