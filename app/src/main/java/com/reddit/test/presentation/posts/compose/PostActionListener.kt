package com.reddit.test.presentation.posts.compose

import com.reddit.test.presentation.posts.mvvm.PostsViewModel


interface PostActionListener {
    fun acceptNewIntention(intention: PostsViewModel.PostsIntention)
}